package io.github.anastasifade.dbms.table.validation.validators;

import io.github.anastasifade.dbms.table.dto.constraint.PrecisionScaleConstraintDto;
import io.github.anastasifade.dbms.table.validation.annotations.ScaleLessThanOrEqualsToPrecision;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static io.github.anastasifade.dbms.common.constants.PostgreSqlLimits.MAX_PRECISION;
import static io.github.anastasifade.dbms.common.constants.PostgreSqlLimits.MAX_SCALE;

public class PrecisionScaleValidator implements ConstraintValidator<ScaleLessThanOrEqualsToPrecision, PrecisionScaleConstraintDto> {

    @Override
    public boolean isValid(PrecisionScaleConstraintDto constraint, ConstraintValidatorContext context) {
        Integer precision = constraint.getPrecision();
        Integer scale = constraint.getScale();

        // returning true for null scale, as well as invalid states validated by other annotations
        if (scale == null || precision == null ||
                scale < 0 || precision <= 0 ||
                scale > MAX_SCALE || precision > MAX_PRECISION) {
            return true;
        }

        return scale <= precision;
    }
}
