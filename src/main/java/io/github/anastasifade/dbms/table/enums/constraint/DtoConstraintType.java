package io.github.anastasifade.dbms.table.enums.constraint;

/**
 * <p>Represents all constraint types that are supported by the application and can be used in client-side
 * constraint DTOs. Used in DTO objects only for convenience. </p>
 * <p>The enum provides a way to decouple constraint metadata storage model from API and frontend logic,
 * and is not used for persistence.</p>
 * <p>The enum represents both column- and table-level constraints, allowing the client to apply
 * a table-level constraint (such as {@code UNIQUE}, {@code CHECK}, {@code PRIMARY KEY}) to a single column without
 * the overhead of creating a separate table constraint DTO.</p>
 */

public enum DtoConstraintType {
    NOT_NULL_CONSTRAINT,
    DEFAULT_CONSTRAINT,
    LENGTH_CONSTRAINT,
    PRECISION_AND_SCALE_CONSTRAINT,
    PRIMARY_KEY_CONSTRAINT,
    UNIQUE_CONSTRAINT,
    CHECK_CONSTRAINT,
    FOREIGN_KEY_CONSTRAINT,
    UNKNOWN
}
