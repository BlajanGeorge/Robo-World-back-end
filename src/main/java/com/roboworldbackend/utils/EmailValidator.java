package com.roboworldbackend.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email format validator
 *
 * @author Blajan George
 */
public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    private final Pattern emailPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(email)) {
            return false;
        }

        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }
}

