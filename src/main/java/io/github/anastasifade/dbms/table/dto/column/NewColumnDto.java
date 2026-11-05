package io.github.anastasifade.dbms.table.dto.column;

import io.github.anastasifade.dbms.table.dto.constraint.ConstraintDto;
import io.github.anastasifade.dbms.table.enums.DataType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class NewColumnDto implements ColumnDto {
    /**
     * Name of the created column.
     * <p>While some databases (e.g., H2) permit identifier length up to 256 characters,
     * the limit here is set to {@code 63} to comply with PostgreSQL's 63-byte identifier limit.
     * <p>Whitelisting allowed characters [upper- and lowercase letters, digits, space and underscore]
     * ensures 63-character limit never exceeds 63 bytes.
     */
    @NotBlank(message = "Column name cannot be blank.")
    @Size(max = 63, message = "Column name cannot exceed 63 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9_ ]+$",
            message = "Unsupported symbols in column name: column name may only contain " +
                    "upper- and lowercase letters, digits, underscores and spaces.")
    private String name;

    /**
     * Data type of the created column. Must be assigned at creation.
     */
    @NotNull(message = "Column must have an assigned data type.")
    private DataType type;

    /**
     * (Optional) Constraints that apply to the given column.
     */
    private Set<@Valid ConstraintDto> constraints;
}
