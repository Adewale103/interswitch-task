package org.interswitch.bookstore.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.interswitch.bookstore.validators.ValidGenreValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidGenreValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidGenre {
    String message() default "Invalid Genre";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
