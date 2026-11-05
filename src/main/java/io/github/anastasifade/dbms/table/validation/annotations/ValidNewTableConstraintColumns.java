package io.github.anastasifade.dbms.table.validation.annotations;

import io.github.anastasifade.dbms.table.validation.validators.NewTableConstraintColumnsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NewTableConstraintColumnsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNewTableConstraintColumns {
    String message() default "Table constraint must reference existing columns.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
