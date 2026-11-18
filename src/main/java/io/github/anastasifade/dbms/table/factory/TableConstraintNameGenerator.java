package io.github.anastasifade.dbms.table.factory;

import io.github.anastasifade.dbms.table.model.ColumnMetadata;
import io.github.anastasifade.dbms.table.model.TableConstraint;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.github.anastasifade.dbms.common.constants.PostgreSqlLimits.MAX_IDENTIFIER_LENGTH;

/**
 * This component generates a unique name for a table constraint.
 * <p>
 * The class uses an abbreviated constraint type as a prefix, and appends table and column names to it,
 * using an underscore as a delimiter.
 * <p>
 * To ensure no illegal symbols are used, identifiers undergo normalization before being appended to the constraint name.
 * This may result in some of the previously unique identifiers becoming non-unique.
 * <p>
 * To avoid potential overlap in constraint names, a hash code is generated for the constraint using table and columns
 * details, as well as the constraint type, and appended to the constraint name.
 * <p>
 * To meet PostgreSQL specifications for the maximum length of constraint names, the generated base gets trimmed first
 * to ensure that hash is never cut off.
 */
@Component
public class TableConstraintNameGenerator {

    private static final String DELIMITER = "_";

    /**
     *
     * @param constraint expects a TableConstraint instance that has been pre-mapped, with table, type and
     *                   columns fields already set.
     * @return String, unique for the given table constraint, with a guaranteed maximum length of 63 (maximum
     * identifier length in PostgreSQL).
     */
    public String generateConstraintName(TableConstraint constraint) {
        String base = new StringBuilder()
                .append(getPrefix(constraint))
                .append(DELIMITER)
                .append(getTable(constraint))
                .append(getColumns(constraint))
                .toString();

        return withHash(constraint, base);
    }

    private String getPrefix(TableConstraint constraint) {
        return constraint.getType().getAbbr();
    }

    private String getTable(TableConstraint constraint) {
        return normalize(constraint.getTable().getName());
    }

    private String getColumns(TableConstraint constraint) {
        return constraint.getColumns()
                .stream()
                .sorted(Comparator.comparingInt(ColumnMetadata::getIndex))
                .map(column -> DELIMITER + normalize(column.getName()))
                .collect(Collectors.joining());
    }

    private String normalize(String str) {
        return str.toLowerCase()
                .replaceAll("[^a-zA-Z0-9_]", "");
    }

    private String withHash(TableConstraint constraint, String name) {
        String hash = String.valueOf(Math.abs(
                Objects.hash(constraint.getTable().getName(),
                        constraint.getType(),
                        constraint.getColumns().stream()
                                .sorted(Comparator.comparingInt(ColumnMetadata::getIndex))
                                .map(ColumnMetadata::getName)
                                .collect(Collectors.joining("_")))));

        if (name.length() + hash.length() > MAX_IDENTIFIER_LENGTH) {
            name = name.substring(0, MAX_IDENTIFIER_LENGTH - hash.length());
        }

        return name + hash;
    }

}