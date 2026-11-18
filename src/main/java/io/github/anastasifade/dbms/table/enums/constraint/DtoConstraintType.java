package io.github.anastasifade.dbms.table.enums.constraint;

import lombok.Getter;

/**
 * <p>Represents all constraint types that are supported by the application and can be used in client-side
 * constraint DTOs. Used in DTO objects only for convenience. </p>
 * <p>The enum provides a way to decouple constraint metadata storage model from API and frontend logic,
 * and is not used for persistence.</p>
 * <p>The enum represents both column- and table-level constraints, allowing the client to apply
 * a table-level constraint (such as {@code UNIQUE}, {@code CHECK}, {@code PRIMARY KEY}) to a single column without
 * the overhead of creating a separate table constraint DTO.</p>
 */

@Getter
public enum DtoConstraintType {
    NOT_NULL_CONSTRAINT(ColumnConstraintType.NOT_NULL, null),
    DEFAULT_CONSTRAINT(ColumnConstraintType.DEFAULT, null),
    LENGTH_CONSTRAINT(ColumnConstraintType.LENGTH, null),
    PRECISION_AND_SCALE_CONSTRAINT(ColumnConstraintType.PRECISION_SCALE, null),
    PRIMARY_KEY_CONSTRAINT(null, TableConstraintType.PRIMARY_KEY),
    UNIQUE_CONSTRAINT(null, TableConstraintType.UNIQUE),
    CHECK_CONSTRAINT(null, TableConstraintType.CHECK),
    FOREIGN_KEY_CONSTRAINT(null, TableConstraintType.FOREIGN_KEY),
    UNKNOWN(null, null);

    private final ColumnConstraintType columnConstraintType;
    private final TableConstraintType tableConstraintType;

    DtoConstraintType(ColumnConstraintType columnConstraintType, TableConstraintType tableConstraintType) {
        this.columnConstraintType = columnConstraintType;
        this.tableConstraintType = tableConstraintType;
    }

    public boolean isColumnConstraint() {
        return columnConstraintType != null;
    }

    public boolean isTableConstraint() {
        return tableConstraintType != null;
    }
}
