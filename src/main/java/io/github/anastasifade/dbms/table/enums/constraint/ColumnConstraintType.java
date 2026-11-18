package io.github.anastasifade.dbms.table.enums.constraint;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerated type representing every column-level constraint type.
 * <p>
 * A constraint is considered to be column-level if it can be applied to no more than one column.
 */
@Getter
public enum ColumnConstraintType {
    NOT_NULL("NOT NULL"),
    DEFAULT("DEFAULT"),
    LENGTH("LENGTH"),
    PRECISION_SCALE("PRECISION AND SCALE");

    private final String sql;

    private static final Map<String, ColumnConstraintType> constraints = new HashMap<>();

    static {
        for (ColumnConstraintType c : ColumnConstraintType.values()) {
            constraints.put(c.sql, c);
        }
    }

    ColumnConstraintType(String sql) {
        this.sql = sql;
    }

    public static ColumnConstraintType get(String constraint) {
        return constraints.get(constraint);
    }
}
