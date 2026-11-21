package io.github.anastasifade.dbms.table.factory;

import io.github.anastasifade.dbms.exception.UnexpectedConstraintType;
import io.github.anastasifade.dbms.table.enums.DataType;
import io.github.anastasifade.dbms.table.enums.constraint.CheckOperation;
import io.github.anastasifade.dbms.table.enums.constraint.TableConstraintType;
import io.github.anastasifade.dbms.table.model.ColumnMetadata;
import io.github.anastasifade.dbms.table.model.TableConstraint;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This component generates a SQL-compliant CHECK constraint expression.
 * <p>
 * The expression generator implements the standard SQL quote escaping for text-based data types as well as
 * date-time values.
 * <p>
 * Note: in the current version, only single-column CHECK constraints are supported.
 */
@Component
public class CheckExpressionBuilder {

    private static final String SPACE = " ";
    private static final String COMMA = ", ";
    private static final String AND = " AND ";

    /**
     *
     * @param constraint pre-mapped valid {@link TableConstraint} with {@link TableConstraintType} {@code = CHECK}.
     * @param operation {@link CheckOperation} for the given constraint.
     * @param checkValues check values. Expects the values to have valid size for given operation
     *                    (e.g., {@code CHECK ... IS NOT NULL} expects 0 values to be checked against,
     *                    and {@code CHECK ... LIKE ...} expects exactly 1 value), as well as have a valid format for
     *                    the data type being checked against (e.g., {@code CHECK ... = ...} constraint applied to
     *                    {@link DataType}{@code = TIMESTAMP_WITH_TIMEZONE} expects a String that can be correctly
     *                    parsed to {@link java.time.ZonedDateTime}).
     * @return SQL-ready String representation of a CHECK expression.
     */

    public String toCheckExpression(TableConstraint constraint, CheckOperation operation, List<String> checkValues) {
        TableConstraintType type = constraint.getType();
        if (type != TableConstraintType.CHECK) {
            throw new UnexpectedConstraintType("Could not create a CHECK expression for constraint type: " + type);
        }

        return new StringBuilder()
                .append(type.getSql())
                .append(SPACE)
                .append("(")
                .append(getColumnsAsString(constraint.getColumns()))
                .append(SPACE)
                .append(operation.getSql())
                .append(getCheckValuesAsString(operation,
                        checkValues,
                        constraint.getColumns().getFirst().getType()))
                .append(")")
                .toString();
    }

    private String getColumnsAsString(List<ColumnMetadata> columns) {
        return columns.stream()
                .map(col -> "\"" + col.getName() + "\"")
                .collect(Collectors.joining(COMMA));
    }

    private String getCheckValuesAsString(CheckOperation operation, List<String> checkValues, DataType dataType) {
        if (checkValues == null || checkValues.isEmpty()) return "";

        return switch (operation) {
            case IS_NOT_NULL, IS_NULL -> "";
            case LIKE, NOT_LIKE, REGEXP -> SPACE + "'" + checkValues.stream()
                    .findFirst()
                    .get()
                    .replace("'", "''") + "'";
            case BETWEEN -> SPACE + getCheckValuesForDataType(dataType, checkValues, AND);
            case IN, NOT_IN -> SPACE + "(" + getCheckValuesForDataType(dataType, checkValues, COMMA) + ")";
            default -> SPACE + getCheckValuesForDataType(dataType, checkValues, COMMA);
        };
    }

    private String getCheckValuesForDataType(DataType type, List<String> values, String delimiter) {
        return switch (type) {
            case VARCHAR, FILE, DATE, TIME, TIME_WITH_TIMEZONE, TIMESTAMP, TIMESTAMP_WITH_TIMEZONE ->
                    values.stream()
                    .map(v -> "'" + v.replace("'", "''") + "'")
                    .collect(Collectors.joining(delimiter));
            default -> String.join(delimiter, values);
        };
    }
}
