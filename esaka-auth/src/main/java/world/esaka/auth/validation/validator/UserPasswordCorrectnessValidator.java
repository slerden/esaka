package world.esaka.auth.validation.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import world.esaka.auth.model.User;
import world.esaka.auth.repository.UserRepository;
import world.esaka.auth.validation.annotation.IsCorrectPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserPasswordCorrectnessValidator implements ConstraintValidator<IsCorrectPassword, User> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(User s, ConstraintValidatorContext constraintValidatorContext) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        if (s.getPassword() != null) {
            User user = userRepository.findByUsername(principal.getUsername());
            if (!passwordEncoder.matches(s.getPassword(), user.getPassword())) {
                return false;
            }
        }
        return true;
    }
}
