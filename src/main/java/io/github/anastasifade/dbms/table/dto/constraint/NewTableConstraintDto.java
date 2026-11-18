package io.github.anastasifade.dbms.table.dto.constraint;

import io.github.anastasifade.dbms.table.validation.annotations.ValidTableConstraintColumnCount;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static io.github.anastasifade.dbms.common.constants.PostgreSqlLimits.MAX_IDENTIFIER_LENGTH;

/**
 * Table-level constraint DTO, used as a nested DTO when passing data to create a new table.
 * <p>The DTO represents a constraint applied to the table, and is intended for table-level
 * constraints that affect multiple columns. Use for single-column constraints is possible, but not encouraged.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidTableConstraintColumnCount
public final class NewTableConstraintDto implements TableConstraintDto {

    /**
     * The names of columns affected by the constraint. At least one column is expected.
     * <p>Must be unique: a single constraint may only apply to a column once.
     * <p>As only quoted identifiers are implemented in user schemas, every identifier is case-sensitive.
     * <p>For example, <code>users</code> and <code>Users</code> would be treated as two unique columns.
     */
    @NotEmpty(message = "Table-level constraint must apply to at least one column.")
    private Set<
            @NotBlank(message = "Column name cannot be blank.")
            @Size(max = MAX_IDENTIFIER_LENGTH, message = "Column name cannot exceed 63 characters.")
            @Pattern(regexp = "^[a-zA-Z0-9_ ]+$",
                    message = "Unsupported symbols in column name: column name may only contain upper- " +
                            "and lowercase letters, digits, underscores and spaces.")
                    String
            > columns;

    /**
     * Constraint applied to the columns.
     * <p>Every constraint type is supported at table level, assuming the amount of columns the constraint applies to
     * is compatible with constraint type (validated by {@code @ValidTableConstraintColumnCount}): e.g.,
     * constraints such as {@code DEFAULT} or {@code NOT NULL}, as well as length restrictions for VARCHAR and
     * precision and scale restrictions for NUMERIC values, may only be applied to one column at a time.
     * <p>Other constraints (e.g., {@code CHECK}, {@code UNIQUE}, etc.) support multiple columns, and require at least
     * one column to be specified.
     */
    @NotNull(message = "Constraint information must be provided when creating a table-level constraint.")
    @Valid
    private ConstraintDto constraint;
}
