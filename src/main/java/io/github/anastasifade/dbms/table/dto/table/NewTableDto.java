package io.github.anastasifade.dbms.table.dto.table;

import io.github.anastasifade.dbms.table.dto.column.NewColumnDto;
import io.github.anastasifade.dbms.table.dto.constraint.NewTableConstraintDto;
import io.github.anastasifade.dbms.table.validation.annotations.UniqueColumns;
import io.github.anastasifade.dbms.table.validation.annotations.ValidNewTableConstraintColumns;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@ValidNewTableConstraintColumns
public class NewTableDto implements TableDto {
    @NotBlank(message = "Table cannot cannot be blank.")
    @Size(max = 63, message = "Table name cannot exceed 63 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9_ ]+$",
            message = "Unsupported symbols in table name: " +
                    "table name may only contain upper [A-Z] and lower [a-z] case letters, underscores and spaces.")
    private String name;

    /**
     * Columns of the new table. Expected to have unique names
     * <p>As only quoted identifiers are implemented in user schemas, every identifier is case-sensitive.
     * <p>For example, <code>users</code> and <code>Users</code> would be treated as two unique columns.
     */
    @NotEmpty(message = "At least one column must be defined when creating a new table.")
    @UniqueColumns
    private Set<@Valid NewColumnDto> columns;

    private Set<@Valid NewTableConstraintDto> constraints;
}
