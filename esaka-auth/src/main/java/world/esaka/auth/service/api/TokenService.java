package world.esaka.auth.service.api;

import com.auth0.jwt.interfaces.DecodedJWT;
import world.esaka.auth.model.User;

public interface TokenService {
    String encode(User userDetails);
    DecodedJWT decode(String token);
}
