package io.github.anastasifade.dbms.table.enums.constraint;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum TableConstraintType {
    PRIMARY_KEY("PRIMARY KEY", "pk"),
    UNIQUE("UNIQUE", "uq"),
    CHECK("CHECK", "check"),
    FOREIGN_KEY("REFERENCES", "fk");

    private final String value;
    private final String abbr;

    private static final Map<String, TableConstraintType> constraints = new HashMap<>();

    static {
        for (TableConstraintType c : TableConstraintType.values()) {
            constraints.put(c.value, c);
        }
    }

    TableConstraintType(String value, String abbr) {
        this.value = value;
        this.abbr = abbr;
    }

    public static TableConstraintType get(String constraint) {
        return constraints.get(constraint);
    }
}
