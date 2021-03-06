package world.esaka.auth.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import world.esaka.auth.configuration.TokenProperties;
import world.esaka.auth.exception.InvalidRefreshTokenException;
import world.esaka.auth.model.RefreshToken;
import world.esaka.auth.model.User;
import world.esaka.auth.repository.RefreshTokenRepository;
import world.esaka.auth.repository.UserRepository;
import world.esaka.auth.service.api.TokenService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TokenServiceDefaultImpl implements TokenService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private TokenProperties properties;
    private String issuer;
    private Algorithm algorithm;
    private JWTVerifier verifier;
    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;

    @Autowired
    public TokenServiceDefaultImpl(TokenProperties properties,
                                   @Value("${spring.application.name}") String issuer,
                                   RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) throws UnsupportedEncodingException {
        this.properties = properties;
        this.issuer = issuer;
        this.algorithm = Algorithm.HMAC256(properties.getSecret());
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.verifier = JWT.require(algorithm).acceptExpiresAt(0).build();
    }

    @Override
    public String encodeAccessToken(User userDetails) {
        LocalDateTime now = LocalDateTime.now();
        try {
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(userDetails.getUsername())
                    .withIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                    .withExpiresAt(Date.from(now.plusSeconds(properties.getMaxAgeSeconds()).atZone(ZoneId.systemDefault()).toInstant()))
                    .withClaim("role", userDetails.getRole().name())
                    .withClaim("email", userDetails.getEmail())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            logger.error("Cannot properly create token", e);
            throw e;
        }
    }


    @Override
    public String updateRefreshToken(String refreshToken) {
        if (!StringUtils.isEmpty(refreshToken)) {
            DecodedJWT decode = decode(refreshToken);
            if (decode.getExpiresAt().after(new Date())) {
                String username = decode.getSubject();
                RefreshToken token = refreshTokenRepository.findByUserUsernameAndHash(username, refreshToken);
                if (token != null) {
                    token.setHash(encodeRefreshToken(token.getUser()));
                    refreshTokenRepository.save(token);
                    return token.getHash();
                } else {
                    throw new AccessDeniedException(String.format("Refresh token is not found for user %s", username));
                }
            }
        }
        return null;
    }

    @Override
    public String createRefreshToken(User user) {
        String username = user.getUsername();
        user = userRepository.findByUsername(username);
        String jwt = encodeRefreshToken(user);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setHash(jwt);
        refreshTokenRepository.save(refreshToken);
        return jwt;
    }

    @Override
    public String encodeRefreshToken(User userDetails) {
        LocalDateTime now = LocalDateTime.now();
        Date expiresAt = Date.from(now.plusSeconds(properties.getMaxAgeRefreshTokenSeconds()).atZone(ZoneId.systemDefault()).toInstant());
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(expiresAt)
                .sign(algorithm);
    }

    @Override
    public DecodedJWT decode(String token) {
        return verifier.verify(token);
    }
}
