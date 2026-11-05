package io.github.anastasifade.dbms.table.validation.annotations;

import io.github.anastasifade.dbms.table.validation.validators.UniqueColumnsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueColumnsValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueColumns {
    String message() default "The column names within the same table must be unique. " +
            "Note: uniqueness is determined without case sensitivity.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
