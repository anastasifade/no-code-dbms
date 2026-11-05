package io.github.anastasifade.dbms.table.enums.constraint;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ColumnConstraintType {
    NOT_NULL("NOT NULL"),
    DEFAULT("DEFAULT"),
    LENGTH("LENGTH"),
    PRECISION_SCALE("PRECISION AND SCALE");

    private final String value;

    private static final Map<String, ColumnConstraintType> constraints = new HashMap<>();

    static {
        for (ColumnConstraintType c : ColumnConstraintType.values()) {
            constraints.put(c.value, c);
        }
    }

    ColumnConstraintType(String value) {
        this.value = value;
    }

    public static ColumnConstraintType get(String constraint) {
        return constraints.get(constraint);
    }
}
