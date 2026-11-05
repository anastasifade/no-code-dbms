package io.github.anastasifade.dbms.table.dto.constraint;

import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;
import io.github.anastasifade.dbms.table.validation.annotations.UniqueCaseSensitive;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
    @Size(max = 63, message = "Table name cannot exceed 63 characters.")
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
    @UniqueCaseSensitive(message = "Columns referenced by the same foreign key must have unique names." +
            "Note: uniqueness is determined without case-sensitivity.")
    @NotEmpty(message = "At least one referenced column must be provided.")
    private Set<
            @NotBlank(message = "Referenced column name cannot be blank.")
            @Size(max = 63, message = "Column name cannot exceed 63 characters.")
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
