package world.esaka.auth.service.impl.security;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import world.esaka.auth.model.Role;
import world.esaka.auth.model.TokenUserDetails;
import world.esaka.auth.service.api.TokenService;

@Service
public class EsakaTokenAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>{

    private final TokenService tokenService;

    @Autowired
    public EsakaTokenAuthenticationUserDetailsService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken) throws UsernameNotFoundException {
        if (preAuthenticatedAuthenticationToken.getPrincipal() != null
                && preAuthenticatedAuthenticationToken.getPrincipal() instanceof String
                && preAuthenticatedAuthenticationToken.getCredentials() instanceof String) {
            DecodedJWT token;
            try {
                token = tokenService.decode((String) preAuthenticatedAuthenticationToken.getPrincipal());
            } catch (InvalidClaimException e) {
                throw new UsernameNotFoundException("Token has been expired", e);
            }
            return new TokenUserDetails(
                    token.getSubject(),
                    null,
                    token.getClaim("email").asString(),
                    Role.valueOf(token.getClaim("role").asString()),
                    token.getToken(),
                    null,
                    token.getSubject());
        } else {
            throw new UsernameNotFoundException("Невозможно извлечь описание пользователя для " + preAuthenticatedAuthenticationToken.getPrincipal());
        }
    }
}
