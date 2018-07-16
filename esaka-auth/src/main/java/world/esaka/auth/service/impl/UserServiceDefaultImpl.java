package world.esaka.auth.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import world.esaka.auth.model.Role;
import world.esaka.auth.model.User;
import world.esaka.auth.repository.UserRepository;
import world.esaka.auth.service.api.UserService;

import java.util.Date;

@Service
public class UserServiceDefaultImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceDefaultImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create(User user) {
        user.setCreateDate(new Date());
        user.setUpdateDate(user.getCreateDate());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User update(String username, User user) {
        User foundUser = userRepository.findByUsername(username);
        if (StringUtils.isNotEmpty(user.getEmail())) {
            foundUser.setEmail(user.getEmail());
        }
        if (StringUtils.isNotEmpty(user.getNewPassword())) {
            foundUser.setPassword(passwordEncoder.encode(user.getNewPassword()));
        }
        userRepository.save(foundUser);
        return foundUser;
    }
}
