package world.esaka.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import world.esaka.auth.model.User;
import world.esaka.auth.service.api.AuthenticationService;
import world.esaka.auth.service.api.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Service
public class BasicAuthenticationService implements AuthenticationService {


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User authenticate(String token) {
        if (token != null && token.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = token.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);
            String username = values[0];
            String rawPassword = values[1];
            User loadedUser = userService.findByUsername(username);
            if (loadedUser == null) {
                throw new UsernameNotFoundException(String.format("User with username %s not found", username));
            }
            if (passwordEncoder.matches(rawPassword, loadedUser.getPassword())) {
                return loadedUser;
            } else {
                throw new UsernameNotFoundException(String.format("User with username %s found, but password is not matches", username));
            }
        }
        return null;
    }
}
