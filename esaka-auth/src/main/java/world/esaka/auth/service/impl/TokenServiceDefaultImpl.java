package world.esaka.auth.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import world.esaka.auth.configuration.TokenProperties;
import world.esaka.auth.model.User;
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

    @Autowired
    public TokenServiceDefaultImpl(TokenProperties properties, @Value("${spring.application.name}") String issuer) throws UnsupportedEncodingException {
        this.properties = properties;
        this.issuer = issuer;
        this.algorithm = Algorithm.HMAC256(properties.getSecret());
        this.verifier = JWT.require(algorithm).acceptExpiresAt(0).build();
    }

    @Override
    public String encode(User userDetails) {
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
    public DecodedJWT decode(String token) {
        return verifier.verify(token);
    }
}
