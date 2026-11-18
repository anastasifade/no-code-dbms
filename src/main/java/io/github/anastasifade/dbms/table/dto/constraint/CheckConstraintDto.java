package io.github.anastasifade.dbms.table.dto.constraint;

import io.github.anastasifade.dbms.table.enums.constraint.CheckOperation;
import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

/**
 * DTO for creation of a {@code CHECK} constraint.
 * <p>
 * Currently, CHECK constraint supports comparing column(s) value(s) against other values,
 * but not against other columns.
 */
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
     * <p>Cannot contain double quotes [""] or semicolons [;].
     * May be null if the check operation does not require comparison against other values (i.e., {@code IS NOT NULL}).
     */
    @UniqueElements(message = "Check constraint values must be unique.")
    private List<@NotBlank(message = "Check constraint value cannot be blank.")
                            String> checkValues;

    @Override
    public DtoConstraintType getType() {
        return DtoConstraintType.CHECK_CONSTRAINT;
    }
}
