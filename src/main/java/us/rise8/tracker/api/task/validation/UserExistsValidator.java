package us.rise8.tracker.api.task.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import us.rise8.tracker.api.user.UserRepository;

public class UserExistsValidator implements ConstraintValidator<UserExists, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintContext) {

        if (id == null) {
            return true;
        }

        return userRepository.existsById(id);
    }
}
