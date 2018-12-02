package world.esaka.auth.spring.security.service;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import world.esaka.auth.model.User;
import world.esaka.auth.service.jwt.JwtService;

public class JwtPreAuthenticatedUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private JwtService jwtService;

    public JwtPreAuthenticatedUserDetailsService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        String tokenString = (String) token.getPrincipal();
        User user;
        try {
            user = jwtService.decodeToken(tokenString);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Exception while decoding token", e);
        }
        return user;
    }
}
