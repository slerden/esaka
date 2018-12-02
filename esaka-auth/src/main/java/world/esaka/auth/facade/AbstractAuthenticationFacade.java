package world.esaka.auth.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import world.esaka.auth.dto.TokenDto;
import world.esaka.auth.exception.AuthenticationException;
import world.esaka.auth.facade.api.TokenAuthenticationFacade;
import world.esaka.auth.model.User;
import world.esaka.auth.service.api.AuthenticationService;
import world.esaka.auth.service.jwt.JwtService;

public abstract class AbstractAuthenticationFacade implements TokenAuthenticationFacade {

    public AbstractAuthenticationFacade(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    private AuthenticationService authenticationService;

    @Autowired
    @Qualifier("accessJwtService")
    private JwtService accessJwtService;

    @Autowired
    @Qualifier("refreshJwtService")
    private JwtService refreshJwtService;

    @Override
    public TokenDto authenticate(String token) {
        if (token == null) throw new AuthenticationException("Token is null");
        User authorization = authenticationService.authenticate(token);
        String refreshToken = refreshJwtService.generateToken(authorization);
        String accessToken = accessJwtService.generateToken(authorization);
        return new TokenDto(accessToken, refreshToken);
    }
}
