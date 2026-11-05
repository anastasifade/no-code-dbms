package io.github.anastasifade.dbms.table.validation.annotations;

import io.github.anastasifade.dbms.table.validation.validators.UniqueCaseSensitiveElementsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueCaseSensitiveElementsValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCaseSensitive {
    String message() default "The elements must be unique.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
