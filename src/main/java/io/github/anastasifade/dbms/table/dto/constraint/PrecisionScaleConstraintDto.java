package io.github.anastasifade.dbms.table.dto.constraint;

import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;
import io.github.anastasifade.dbms.table.validation.annotations.ScaleLessThanOrEqualsToPrecision;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.github.anastasifade.dbms.common.constants.PostgreSqlLimits.MAX_PRECISION;
import static io.github.anastasifade.dbms.common.constants.PostgreSqlLimits.MAX_SCALE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ScaleLessThanOrEqualsToPrecision
public final class PrecisionScaleConstraintDto implements ConstraintDto {

    /**
     * Precision of a numeric value (total count of significant digits).
     * <p>Must be a positive number, and cannot exceed 1000 to comply with PostgreSQL 18 specifications.
     */
    @NotNull(message = "Precision must be specified.")
    @Positive(message = "Precision must be greater than 0.")
    @Max(value = MAX_PRECISION, message = "Precision cannot exceed 1000.")
    private Integer precision;

    /**
     * (Optional) Scale of a numeric value. If null, defaults to 0.
     * <p>
     * If provided, must be a positive number or zero:
     * despite support for negative scale in PostgreSQL, many databases (e.g., H2) require the scale to be a
     * non-negative number, so negative values are rejected to avoid conflict.
     * <p>
     * Cannot exceed precision: while PostgreSQL allows any scale in range [-1000; 1000],
     * the SQL standard requires the scale to be in the range 0 to precision.
     */
    @PositiveOrZero(message = "Scale must be a non-negative number.")
    @Max(value = MAX_SCALE, message = "Scale cannot exceed 1000.")
    private Integer scale;

    @Override
    public DtoConstraintType getType() {
        return DtoConstraintType.PRECISION_AND_SCALE_CONSTRAINT;
    }
}
