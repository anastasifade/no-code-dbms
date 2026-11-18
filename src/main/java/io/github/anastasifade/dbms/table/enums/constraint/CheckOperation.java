package io.github.anastasifade.dbms.table.enums.constraint;

import lombok.Getter;

/**
 * Enumerated type for supported {@code CHECK} constraint operations.
 * Represents most PostgreSQL-compatible CHECK constraint operations.
 * <p>
 * Currently not supported:
 * <ul>
 *     <li> {@code AND} - redundant in a GUI-driven no-code DMBS. Instead, two separate constraints are generated.
 *     <li> {@code OR} - due to complex implementation in a GUI-driven no-code environment.
 *     <li> {@code NOT} - not supported directly in this version. Instead, access to NOT operand is provided
 *     indirectly through other operands: {@link NOT_EQUALS}, {@link NOT_IN}, {@link NOT_LIKE}. {@link IS_NOT_NULL}.
 * </ul>
 */
@Getter
public enum CheckOperation {
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">="),
    EQUALS("="),
    NOT_EQUALS("<>"),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<="),
    IN("IN"),
    NOT_IN("NOT_IN"),
    BETWEEN("BETWEEN"),
    LIKE("LIKE"),
    NOT_LIKE("NOT LIKE"),
    REGEXP("REGEXP"),
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL");

    private final String sql;

    CheckOperation(String sql) {
        this.sql = sql;
    }
}
