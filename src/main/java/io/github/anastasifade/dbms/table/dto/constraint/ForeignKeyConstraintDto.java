package io.github.anastasifade.dbms.table.dto.constraint;

import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static io.github.anastasifade.dbms.common.constants.PostgreSqlLimits.MAX_IDENTIFIER_LENGTH;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ForeignKeyConstraintDto implements ConstraintDto {

    /**
     * Name of the table referenced by the foreign key.
     * <p>Uses name instead of an ID to allow a table being created to reference itself.
     * Unique table names ensure no ambiguity when determining which table is referenced.
     * <p>Only whitelisted characters are permitted: upper- and lowercase letters, digits, underscore and space.
     */
    @NotNull(message = "For 'FOREIGN KEY' constraint, a referenced table name must be provided.")
    @Size(max = MAX_IDENTIFIER_LENGTH, message = "Table name cannot exceed 63 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9_ ]+$",
            message = "Unsupported symbols in table name: " +
                    "table name may only contain upper- and lowercase letters, digits, underscores and spaces.")
    private String referencedTable;

    /**
     * Name of the column(s) referenced by the foreign key.
     * <p>Uses name instead of an ID to allow a table being created to reference itself.
     * Unique table-column name pairs ensure no ambiguity when determining which column is referenced.
     * <p>At least one value is expected.
     * <p>Only whitelisted characters are permitted: upper- and lowercase letters, digits, underscore and space.
     */
    @NotEmpty(message = "At least one referenced column must be provided.")
    private Set<
            @NotBlank(message = "Referenced column name cannot be blank.")
            @Size(max = MAX_IDENTIFIER_LENGTH, message = "Column name cannot exceed 63 characters.")
            @Pattern(regexp = "^[a-zA-Z0-9_ ]+$",
                    message = "Unsupported symbols in column name: " +
                            "column name may only contain upper- and lowercase letters, digits, underscores and spaces.")
                    String
            > referencedColumns;

    @Override
    public DtoConstraintType getType() {
        return DtoConstraintType.FOREIGN_KEY_CONSTRAINT;
    }
}
