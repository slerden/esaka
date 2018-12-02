package world.esaka.auth.service.jwt;

import world.esaka.auth.exception.AuthenticationException;
import world.esaka.auth.model.User;

public interface JwtService {
    String generateToken(User user);
    User decodeToken(String token);
    static String getTokenFromAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new AuthenticationException("No JWT token found in request headers");
        }
        return header.substring(7);
    }
    static String AUTHORIZATION_HEADER_KEY = "Authorization";
}
