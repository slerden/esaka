package world.esaka.auth.service.api;

import world.esaka.auth.model.User;

public interface UserService {
    User findByUsername(String username);
    User findById(Long id);
    User create(User user);
    User changePassword(String username, String oldPassword, String newPassword);
}
