package org.interswitch.bookstore.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.interswitch.bookstore.annotation.ValidISBN;


public class ValidISBNValidator implements ConstraintValidator<ValidISBN, String > {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^[\\d-]+$");
    }
}
