package world.esaka.auth.facade.api;

import world.esaka.auth.dto.TokenDto;

public interface TokenAuthenticationFacade {
    TokenDto authenticate(String token);
}
