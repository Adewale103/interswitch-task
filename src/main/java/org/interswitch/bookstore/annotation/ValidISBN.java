package org.interswitch.bookstore.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.interswitch.bookstore.validators.ValidISBNValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidISBNValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidISBN {
    String message() default "Invalid Isbn";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
