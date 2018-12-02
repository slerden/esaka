package world.esaka.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import world.esaka.auth.dto.TokenDto;
import world.esaka.auth.exception.InvalidRefreshTokenException;
import world.esaka.auth.model.TokenUserDetails;

@RestController
public class TokenController {

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public TokenDto handleInvalidRefreshTokenException(InvalidRefreshTokenException ex) {
        return new TokenDto(null, null);
    }


    @GetMapping("/token/authenticate")
    public TokenDto authToken(@AuthenticationPrincipal TokenUserDetails userDetails) {
        return new TokenDto(userDetails.getAccessToken(), userDetails.getRefreshToken());
    }

    @GetMapping("/token/refresh")
    public TokenDto refreshToken(@AuthenticationPrincipal TokenUserDetails userDetails) {
        return new TokenDto(userDetails.getAccessToken(), userDetails.getRefreshToken());
    }
}
