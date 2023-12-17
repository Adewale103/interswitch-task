package org.interswitch.bookstore.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.interswitch.bookstore.annotation.ValidTitle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidTitleValidator implements ConstraintValidator<ValidTitle, String > {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value.matches("^[a-zA-Z0-9 ]+$");
    }
}
