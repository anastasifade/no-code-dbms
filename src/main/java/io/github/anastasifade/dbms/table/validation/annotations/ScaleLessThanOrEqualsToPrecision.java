package io.github.anastasifade.dbms.table.validation.annotations;

import io.github.anastasifade.dbms.table.validation.validators.PrecisionScaleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PrecisionScaleValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScaleLessThanOrEqualsToPrecision {
    String message() default "Scale cannot exceed precision.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
