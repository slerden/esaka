package world.esaka.auth.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import world.esaka.auth.facade.AbstractAuthenticationFacade;
import world.esaka.auth.service.api.AuthenticationService;
@Service
public class RefreshTokenAuthenticationFacade extends AbstractAuthenticationFacade {

    @Autowired
    public RefreshTokenAuthenticationFacade(@Qualifier("refreshTokenAuthenticationService") AuthenticationService authenticationService) {
        super(authenticationService);
    }
}
