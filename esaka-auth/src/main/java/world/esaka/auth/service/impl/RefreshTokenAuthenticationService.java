package world.esaka.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import world.esaka.auth.model.User;
import world.esaka.auth.service.api.AuthenticationService;
import world.esaka.auth.service.jwt.JwtService;

@Service
public class RefreshTokenAuthenticationService implements AuthenticationService {

    @Autowired
    @Qualifier("refreshJwtService")
    private JwtService jwtService;

    @Override
    public User authenticate(String token) {
        String tokenFromAuthorizationHeader = JwtService.getTokenFromAuthorizationHeader(token);
        return jwtService.decodeToken(tokenFromAuthorizationHeader);
    }
}
