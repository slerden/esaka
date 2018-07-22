package world.esaka.auth.service.impl.security;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import world.esaka.auth.model.TokenUserDetails;
import world.esaka.auth.model.User;
import world.esaka.auth.repository.UserRepository;
import world.esaka.auth.service.api.TokenService;

import java.util.Date;

@Service
public class EsakaRefreshTokenUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>{

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Autowired
    public EsakaRefreshTokenUserDetailsService(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (token.getPrincipal() != null
                && token.getPrincipal() instanceof String
                && token.getCredentials() instanceof String) {
            DecodedJWT jwt = null;
            try {
                jwt = tokenService.decode((String) token.getPrincipal());
            } catch (InvalidClaimException e) {
                throw new UsernameNotFoundException("Token has been expired", e);
            }
            Date expiresAt = jwt.getExpiresAt();
            if (!expiresAt.after(new Date())) {
                throw new UsernameNotFoundException("Token has been expired");
            }
            User user = userRepository.findByUsername(jwt.getSubject());
            return getUserDetails(user, jwt.getToken());
        }
        return null;
    }

    private TokenUserDetails getUserDetails(User user, String refreshToken) {
        return new TokenUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(), tokenService.encodeAccessToken(user), tokenService.updateRefreshToken(refreshToken), user.getUsername());
    }
}
