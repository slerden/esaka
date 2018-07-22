package world.esaka.auth.service.api;

import com.auth0.jwt.interfaces.DecodedJWT;
import world.esaka.auth.model.RefreshToken;
import world.esaka.auth.model.User;

public interface TokenService {
    String encodeAccessToken(User userDetails);

    String encodeRefreshToken(User userDetails);

    String updateRefreshToken(String refreshToken);

    String createRefreshToken(User user);

    DecodedJWT decode(String token);
}
