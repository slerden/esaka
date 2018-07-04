package world.esaka.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import world.esaka.auth.model.TokenUserDetails;
import world.esaka.auth.model.User;
import world.esaka.auth.service.api.TokenService;
import world.esaka.auth.service.api.UserService;

@Service
public class EsakaUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public EsakaUserDetailsService(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with name %s not found", s));
        }
        return getUserDetails(user);
    }

    private TokenUserDetails getUserDetails(User user) {
        return new TokenUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(), tokenService.encode(user), user.getUsername());
    }
}
