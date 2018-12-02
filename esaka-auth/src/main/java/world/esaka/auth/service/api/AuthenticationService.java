package world.esaka.auth.service.api;

import world.esaka.auth.model.User;

public interface AuthenticationService {
    User authenticate(String token);
}
