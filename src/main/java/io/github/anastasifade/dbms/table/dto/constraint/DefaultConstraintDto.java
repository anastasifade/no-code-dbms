package io.github.anastasifade.dbms.table.dto.constraint;

import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class DefaultConstraintDto implements ConstraintDto {

    /**
     * Default value for a column. Cannot contain quotes ['', ""] or semicolons [;].
     */
    @NotNull(message = "Default value must be specified when applying DEFAULT constraint.")
    @Pattern(regexp = "^[^;'\"]*$", message = "Default values cannot contain quotes ['', \"\"] or semicolons [;].")
    private String defaultValue;

    @Override
    public DtoConstraintType getType() {
        return DtoConstraintType.DEFAULT_CONSTRAINT;
    }
}
