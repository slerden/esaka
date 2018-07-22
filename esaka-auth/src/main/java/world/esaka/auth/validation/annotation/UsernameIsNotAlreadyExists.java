package world.esaka.auth.validation.annotation;

import world.esaka.auth.validation.validator.UsernameIsNotAlreadyExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameIsNotAlreadyExistsValidator.class)
public @interface UsernameIsNotAlreadyExists {
    String message() default "{javax.validation.constraints.UsernameIsNotAlreadyExists.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
