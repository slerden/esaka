package world.esaka.auth.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import world.esaka.auth.dto.ChangePasswordDto;
import world.esaka.auth.model.User;
import world.esaka.auth.repository.UserRepository;

@Component
public class ChangePasswordDtoValidator implements Validator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ChangePasswordDtoValidator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ChangePasswordDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ChangePasswordDto passwordDto = (ChangePasswordDto) o;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (passwordDto != null) {
            User user = userRepository.findByUsername(userDetails.getUsername());
            if (!passwordEncoder.encode(passwordDto.getCurrentPassword()).equals(user.getPassword())) {
                errors.reject("Текущий пароль указан неверно");
            }
        }
    }
}
