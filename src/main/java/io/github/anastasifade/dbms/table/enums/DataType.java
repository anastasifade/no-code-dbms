package io.github.anastasifade.dbms.table.enums;

import lombok.Getter;

@Getter
public enum DataType {
    VARCHAR("VARCHAR"),
    TEXT("TEXT"),
    BOOLEAN("BOOLEAN"),
    INTEGER("INTEGER"),
    BIGINT("BIGINT"),
    NUMERIC("NUMERIC"),
    FLOAT("REAL"),
    DOUBLE("DOUBLE PRECISION"),
    DATE("DATE"),
    TIME("TIME"),
    TIMESTAMP("TIMESTAMP");

    private final String sqlType;

    DataType(String sqlType) {
        this.sqlType = sqlType;
    }

    public static DataType get(String type) {
        return DataType.valueOf(type.toUpperCase());
    }
}
