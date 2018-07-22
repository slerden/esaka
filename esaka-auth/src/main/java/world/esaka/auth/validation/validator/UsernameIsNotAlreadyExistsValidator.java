package world.esaka.auth.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import world.esaka.auth.repository.UserRepository;
import world.esaka.auth.validation.annotation.UsernameIsNotAlreadyExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameIsNotAlreadyExistsValidator implements ConstraintValidator<UsernameIsNotAlreadyExists, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isEmpty(value) || !userRepository.existsByUsername(value);
    }
}
