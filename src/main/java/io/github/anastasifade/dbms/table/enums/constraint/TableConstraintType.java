package io.github.anastasifade.dbms.table.enums.constraint;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerated type representing every table-level constraint type.
 * <p>
 * A constraint is considered to be table-level is it can affect multiple columns simultaneously.
 */
@Getter
public enum TableConstraintType {
    PRIMARY_KEY("PRIMARY KEY", "pk"),
    UNIQUE("UNIQUE", "uq"),
    CHECK("CHECK", "check"),
    FOREIGN_KEY("REFERENCES", "fk");

    private final String sql;
    private final String abbr;

    private static final Map<String, TableConstraintType> constraints = new HashMap<>();

    static {
        for (TableConstraintType c : TableConstraintType.values()) {
            constraints.put(c.sql, c);
        }
    }

    TableConstraintType(String sql, String abbr) {
        this.sql = sql;
        this.abbr = abbr;
    }

    public static TableConstraintType get(String constraint) {
        return constraints.get(constraint);
    }
}
