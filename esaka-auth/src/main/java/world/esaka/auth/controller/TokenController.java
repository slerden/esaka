package world.esaka.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import world.esaka.auth.dto.TokenDto;
import world.esaka.auth.exception.AuthenticationException;
import world.esaka.auth.facade.api.TokenAuthenticationFacade;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TokenController {

    @ExceptionHandler({UsernameNotFoundException.class, AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public TokenDto handleInvalidRefreshTokenException() {
        return new TokenDto(null, null);
    }

    @Autowired
    @Qualifier("refreshTokenAuthenticationFacade")
    private TokenAuthenticationFacade refreshTokenAuthenticationFacade;


    @Autowired
    @Qualifier("basicAuthenticationFacade")
    private TokenAuthenticationFacade basicTokenAuthenticationFacade;


    @GetMapping("/token/refresh")
    public TokenDto refreshToken(HttpServletRequest request) {
        return refreshTokenAuthenticationFacade.authenticate(request.getHeader("Authorization"));
    }

    @GetMapping("/auth")
    public TokenDto baseAuth(HttpServletRequest request) {
        return basicTokenAuthenticationFacade.authenticate(request.getHeader("Authorization"));
    }
}
