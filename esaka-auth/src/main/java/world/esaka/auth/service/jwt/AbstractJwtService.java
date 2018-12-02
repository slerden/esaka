package world.esaka.auth.service.jwt;

import io.jsonwebtoken.security.Keys;
import world.esaka.auth.configuration.TokenProperties;

import javax.crypto.SecretKey;

public class AbstractJwtService {

    protected final String issuer;

    protected final SecretKey secretKey;

    public AbstractJwtService(TokenProperties tokenProperties, String issuer) {
        this.secretKey = Keys.hmacShaKeyFor(tokenProperties.getSecret().getBytes());
        this.issuer = issuer;
    }
}
