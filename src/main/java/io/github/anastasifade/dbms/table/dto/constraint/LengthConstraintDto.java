package io.github.anastasifade.dbms.table.dto.constraint;

import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class LengthConstraintDto implements ConstraintDto {
    /**
     * Maximum number of characters allowed for a VARCHAR value.
     * <p>Must be greater than zero and cannot exceed 10,485,760, in accordance with PostgreSQL 18 specifications.
     */
    @NotNull(message = "Length restriction must be specified.")
    @Positive(message = "Length restriction must be greater than 0.")
    @Max(value = 10_485_760, message = "Length cannot exceed 10,485,760.")
    private Integer length;

    @Override
    public DtoConstraintType getType() {
        return DtoConstraintType.LENGTH_CONSTRAINT;
    }
}
