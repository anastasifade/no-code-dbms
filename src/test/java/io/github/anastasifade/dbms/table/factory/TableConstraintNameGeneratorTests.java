package io.github.anastasifade.dbms.table.factory;

import io.github.anastasifade.dbms.table.enums.DataType;
import io.github.anastasifade.dbms.table.enums.constraint.TableConstraintType;
import io.github.anastasifade.dbms.table.model.ColumnMetadata;
import io.github.anastasifade.dbms.table.model.TableConstraint;
import io.github.anastasifade.dbms.table.model.TableMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.github.anastasifade.dbms.common.constants.PostgreSqlLimits.MAX_IDENTIFIER_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;

public class TableConstraintNameGeneratorTests {

    private static final String DELIMITER = "_";
    private static final String TABLE_NAME = "TEST_TABLE";
    private static final String COLUMN_NAME = "TEST_COLUMN";

    private TableMetadata table;
    private ColumnMetadata column;


    private final TableConstraintNameGenerator generator = new TableConstraintNameGenerator();

    @BeforeEach
    void setUp() {
        table = TableMetadata.builder()
                .name(TABLE_NAME)
                .build();

        column = ColumnMetadata.builder()
                .table(table)
                .name(COLUMN_NAME)
                .type(DataType.VARCHAR)
                .index(0)
                .build();
    }

    @Test
    void test_shouldGenerateName_withExpectedPattern() {
        TableConstraint constraint = getConstraint(TableConstraintType.UNIQUE, table, List.of(column));

        String expectedName = TableConstraintType.UNIQUE.getAbbr() +
                DELIMITER +
                table.getName().toLowerCase() +
                DELIMITER +
                column.getName().toLowerCase() +
                reproduceHash(constraint);

        assertThat(generator.generateConstraintName(constraint)).isEqualTo(expectedName);
    }

    @Test
    void test_shouldRemoveSpacesFromIdentifiers() {
        table.setName("i l o v e s p a c e s");
        TableConstraint constraint = getConstraint(TableConstraintType.UNIQUE, table, List.of(column));

        assertThat(generator.generateConstraintName(constraint)).doesNotContainAnyWhitespaces();
    }

    @Test
    void test_trimsNameToFitMaxIdentifierLength() {
        column.setName("a".repeat(MAX_IDENTIFIER_LENGTH + 1));
        TableConstraint constraint = getConstraint(TableConstraintType.UNIQUE, table, List.of(column));

        assertThat(generator.generateConstraintName(constraint).length()).isLessThanOrEqualTo(MAX_IDENTIFIER_LENGTH);
    }

    @Test
    void test_whenNameTooLong_hashNeverTrimmed() {
        column.setName("a".repeat(MAX_IDENTIFIER_LENGTH + 1));
        TableConstraint constraint = getConstraint(TableConstraintType.UNIQUE, table, List.of(column));

        String hash = reproduceHash(constraint);

        assertThat(generator.generateConstraintName(constraint)).endsWith(hash);
    }

    @Test
    void test_generatesUniqueName_forTableAndColumnWithNamesNotUniqueWhenNormalized() {
        TableMetadata tableB = TableMetadata.builder()
                .name(TABLE_NAME.toLowerCase())
                .build();

        ColumnMetadata columnB = ColumnMetadata.builder()
                .name(COLUMN_NAME.toLowerCase())
                .table(table)
                .index(0)
                .type(DataType.VARCHAR)
                .build();

        TableConstraint constraintA = getConstraint(TableConstraintType.PRIMARY_KEY, table, List.of(column));
        TableConstraint constraintB = getConstraint(TableConstraintType.PRIMARY_KEY, tableB, List.of(columnB));

        assertThat(generator.generateConstraintName(constraintA))
                .isNotEqualTo(generator.generateConstraintName(constraintB));
    }

    private TableConstraint getConstraint(TableConstraintType type, TableMetadata table, List<ColumnMetadata> columns) {
        return TableConstraint.builder()
                .type(type)
                .table(table)
                .columns(columns)
                .build();
    }

    private String reproduceHash(TableConstraint constraint) {
        return String.valueOf(Math.abs(
                Objects.hash(constraint.getTable().getName(),
                        constraint.getType(),
                        constraint.getColumns().stream()
                                .sorted(Comparator.comparingInt(ColumnMetadata::getIndex))
                                .map(ColumnMetadata::getName)
                                .collect(Collectors.joining("_")))));
    }
}
