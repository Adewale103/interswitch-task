package org.interswitch.bookstore.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.interswitch.bookstore.annotation.ValidGenre;


public class ValidGenreValidator implements ConstraintValidator<ValidGenre, String > {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value.matches("^(FICTION|THRILLER|MYSTERY|POETRY|HORROR|SATIRE)$");
    }
}
