package dev.anhpd.utility;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = DobValidator.class)
public @interface DobConstraint {
    String message() default "INVALID_DOB";

    int min();

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
