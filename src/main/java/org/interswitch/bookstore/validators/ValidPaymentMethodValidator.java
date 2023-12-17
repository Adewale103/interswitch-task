package org.interswitch.bookstore.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.interswitch.bookstore.annotation.ValidGenre;
import org.interswitch.bookstore.annotation.ValidPaymentMethod;


public class ValidPaymentMethodValidator implements ConstraintValidator<ValidPaymentMethod, String > {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value.matches("^(USSD|TRANSFER|ONLINE_BANKING)$");
    }
}
