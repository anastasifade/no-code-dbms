package io.github.anastasifade.dbms.table.enums;

import lombok.Getter;

/**
 * Enumerated type that represents every supported data type.
 * <p>
 * Several of the data types defined in PostgreSQL are not supported:
 * <ul>
 *     <li>{@code decimal} - redundant, equivalent to {@link #NUMERIC}.</li>
 *     <li>{@code smallserial}, {@code serial} and {@code bigserial} - rarely used in modern SQL, as
 *     the benefits of auto-incrementing can be achieved by using {@link #BIGINT} paired with an appropriate
 *     generation strategy.</li>
 *     <li>{@code money} - not universally supported by other databases. A supported {@link #NUMERIC} type
 *     allows handling monetary value with similar precision.</li>
 *     <li>{@code character} - offers no performance benefits and uses storage less efficiently than
 *     the supported {@link #VARCHAR} type.</li>
 *     <li>{@code text} - not universally supported by other databased. Has no performance or storage benefits
 *     as compared to using a supported type {@link VARCHAR} without length specifier.</li>
 *     <li>{@code bytea} - major differences exist in binary data storage in PostgreSQL ({@code bytea}
 *     as compared to the SQL standard {@code BLOB}. Binary data storage is not supported to avoid cross-compatibility
 *     issues. As an alternative, the application supports saving a file locally and storing a {@link #URL}
 *     pointing to the file. URL storage is handled using the {@code varchar} data type.</li>
 *     <li>Precision for Date/Time values - not supported in the current version.</li>
 *     <li>{@code internal} - not supported in the current version.</li>
 *     <li>{@code enum} - not supported in the current version. Instead, the existing {@link #VARCHAR} type
 *     may be used, paired with a {@code CHECK (column_name) IN (allowed values)} constraint.</li>
 *     <li>Geometric types are not supported in the current version.</li>
 *     <li>Network address types, text search types, range types and other PostgreSQL-specific data types
 *     are not supported due to cross-compatibility issues.</li>
 *     <li>{@code json} - not supported in the current version due to low demand for JSON value storage in a
 *     GUI-driven no-code database. Instead, {@link #VARCHAR} may be used to store a JSON string.</li>
 *     <li>Arrays are not supported in the current version.</li>
 *     <li>Composite types are not supported. While a part of SQL standard, implementation varies significantly from
 *     database to database.</li>
 *     </ul>
 */
@Getter
public enum DataType {
    VARCHAR("VARCHAR"),
    URL("VARCHAR"),
    BOOLEAN("BOOLEAN"),
    SMALLINT("SMALLINT"),
    INTEGER("INTEGER"),
    BIGINT("BIGINT"),
    NUMERIC("NUMERIC"),
    FLOAT("REAL"),
    DOUBLE("DOUBLE PRECISION"),
    DATE("DATE"),
    TIME("TIME"),
    TIME_WITH_TIMEZONE("TIME WITH TIME ZONE"),
    TIMESTAMP("TIMESTAMP"),
    TIMESTAMP_WITH_TIMEZONE("TIMESTAMP WITH TIME ZONE");

    private final String sql;

    DataType(String sql) {
        this.sql = sql;
    }
}
