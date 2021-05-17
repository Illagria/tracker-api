package us.rise8.tracker.api.task.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UserExistsValidator.class)
@Documented
public @interface UserExists {

    String message() default "user requested for this task doesn't exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
