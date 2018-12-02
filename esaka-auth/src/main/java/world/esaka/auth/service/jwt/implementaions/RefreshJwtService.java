package world.esaka.auth.service.jwt.implementaions;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import world.esaka.auth.configuration.TokenProperties;
import world.esaka.auth.model.User;
import world.esaka.auth.service.jwt.AbstractJwtService;
import world.esaka.auth.service.jwt.JwtService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class RefreshJwtService extends AbstractJwtService implements JwtService {

    private static final long TOKEN_EXPIRATION_DAYS_TIMEOUT = 15L;

    @Autowired
    public RefreshJwtService(TokenProperties tokenProperties, @Value("${spring.application.name}") String issuer) {
        super(tokenProperties, issuer);
    }

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(user.getUsername())
                .setExpiration(Date.from(LocalDateTime.now().plusDays(TOKEN_EXPIRATION_DAYS_TIMEOUT).atZone(ZoneId.systemDefault()).toInstant()))
                .setIssuedAt(new Date())
                .setId(String.valueOf(user.getId()))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public User decodeToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        User result = new User();
        Claims body = claimsJws.getBody();
        result.setUsername(body.getSubject());
        return result;
    }
}
