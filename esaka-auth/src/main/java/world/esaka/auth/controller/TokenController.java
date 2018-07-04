package world.esaka.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import world.esaka.auth.model.TokenUserDetails;

@RestController
public class TokenController {

    @GetMapping("/token")
    public String getToken(@AuthenticationPrincipal TokenUserDetails userDetails) {
        return userDetails.getToken();
    }
}
