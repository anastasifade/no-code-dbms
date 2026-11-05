package io.github.anastasifade.dbms.table.dto.constraint;

import io.github.anastasifade.dbms.table.enums.constraint.CheckOperation;
import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class CheckConstraintDto implements ConstraintDto {
    /**
     * Operand used in the CHECK expression.
     */
    private CheckOperation checkOperation;

    /**
     * (Optional) Set of values compared against constrained columns in a check operation.
     * <p>Cannot contain quotes ['', ""] or semicolons [;].
     * May be null if the check operation does not require comparison against other values (i.e., {@code IS NOT NULL}).
     */
    private Set<@Pattern(regexp = "^[^;'\"]*$",
            message = "Check comparison values cannot contain quotes ['', \"\"] or semicolons [;].")
            String> checkValues;

    @Override
    public DtoConstraintType getType() {
        return DtoConstraintType.CHECK_CONSTRAINT;
    }
}
