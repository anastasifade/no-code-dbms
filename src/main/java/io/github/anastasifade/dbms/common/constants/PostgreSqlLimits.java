package io.github.anastasifade.dbms.common.constants;

public class PostgreSqlLimits {
    private PostgreSqlLimits() {
    }

    public static final int MAX_IDENTIFIER_LENGTH = 63;
    public static final int MAX_SPECIFIED_VARCHAR_LENGTH = 10_485_760;
    public static final int MAX_PRECISION = 1000;
    public static final int MAX_SCALE = MAX_PRECISION;
}
