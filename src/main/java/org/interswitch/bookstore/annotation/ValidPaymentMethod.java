package org.interswitch.bookstore.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.interswitch.bookstore.validators.ValidGenreValidator;
import org.interswitch.bookstore.validators.ValidPaymentMethodValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidPaymentMethodValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPaymentMethod {
    String message() default "Invalid Payment Method";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
