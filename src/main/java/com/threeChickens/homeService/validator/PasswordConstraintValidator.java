package com.threeChickens.homeService.validator;

import com.google.common.base.Joiner;
import com.threeChickens.homeService.annotation.PasswordConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;


import java.util.Arrays;
import java.util.List;

public class PasswordConstraintValidator  implements ConstraintValidator<PasswordConstraint, String> {
    @Override
    public void initialize(PasswordConstraint arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(List.of(
                new LengthRule(6)));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }
}
