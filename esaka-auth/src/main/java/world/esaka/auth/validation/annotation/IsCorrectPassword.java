package world.esaka.auth.validation.annotation;

import world.esaka.auth.validation.validator.UserPasswordCorrectnessValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserPasswordCorrectnessValidator.class)
public @interface IsCorrectPassword {
    String message() default "{javax.validation.constraints.IsCorrectPassword.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
