package io.github.anastasifade.dbms.table.factory;

import io.github.anastasifade.dbms.table.enums.DataType;
import io.github.anastasifade.dbms.table.enums.constraint.CheckOperation;
import io.github.anastasifade.dbms.table.enums.constraint.TableConstraintType;
import io.github.anastasifade.dbms.table.model.ColumnMetadata;
import io.github.anastasifade.dbms.table.model.TableConstraint;
import io.github.anastasifade.dbms.table.model.TableMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckExpressionBuilderTests {

    private final CheckExpressionBuilder builder = new CheckExpressionBuilder();

    private ColumnMetadata column;
    private TableMetadata table;

    @BeforeEach
    void setUp() {
        column = ColumnMetadata.builder()
                .name("test_column")
                .type(DataType.VARCHAR)
                .build();

        table = TableMetadata.builder()
                .name("test_table")
                .columns(List.of(column))
                .build();
    }

    @Test
    void test_returnsCorrectExpression_forNoValuesOperation() {
        String expected = "CHECK (\"test_column\" IS NOT NULL)";

        TableConstraint constraint = TableConstraint.builder()
                .type(TableConstraintType.CHECK)
                .columns(List.of(column))
                .table(table)
                .build();

        assertThat(builder.toCheckExpression(constraint, CheckOperation.IS_NOT_NULL, List.of())).isEqualTo(expected);
    }

    @Test
    void test_returnsCorrectExpression_forSingleValueOperation_numericDataType() {
        String expected = "CHECK (\"test_column\" > 5)";

        column.setType(DataType.NUMERIC);
        TableConstraint constraint = TableConstraint.builder()
                .type(TableConstraintType.CHECK)
                .columns(List.of(column))
                .table(table)
                .build();

        assertThat(builder.toCheckExpression(constraint, CheckOperation.GREATER_THAN, List.of("5")))
                .isEqualTo(expected);
    }

    @Test
    void test_returnsCorrectExpression_forSingleValueOperation_textDataType() {
        String expected = "CHECK (\"test_column\" <> 'test')";

        TableConstraint constraint = TableConstraint.builder()
                .type(TableConstraintType.CHECK)
                .columns(List.of(column))
                .table(table)
                .build();

        assertThat(builder.toCheckExpression(constraint, CheckOperation.NOT_EQUALS, List.of("test")))
                .isEqualTo(expected);
    }

    @Test
    void test_correctlyHandles_singleQuotes_inTextDataTypes() {
        String expected = "CHECK (\"test_column\" <> 'O''Connor')";

        TableConstraint constraint = TableConstraint.builder()
                .type(TableConstraintType.CHECK)
                .columns(List.of(column))
                .table(table)
                .build();

        assertThat(builder.toCheckExpression(constraint, CheckOperation.NOT_EQUALS, List.of("O'Connor")))
                .isEqualTo(expected);
    }

    @Test
    void test_returnsCorrectExpression_forBetween_checkOperation_onNumericTypes() {
        String expected = "CHECK (\"test_column\" BETWEEN 1 AND 10)";

        column.setType(DataType.INTEGER);
        TableConstraint constraint = TableConstraint.builder()
                .type(TableConstraintType.CHECK)
                .columns(List.of(column))
                .table(table)
                .build();

        assertThat(builder.toCheckExpression(constraint, CheckOperation.BETWEEN, List.of("1", "10")))
                .isEqualTo(expected);
    }

    @Test
    void test_returnsCorrectExpression_forBetween_checkOperation_onTextTypes() {
        String expected = "CHECK (\"test_column\" BETWEEN 'a' AND 'b')";

        TableConstraint constraint = TableConstraint.builder()
                .type(TableConstraintType.CHECK)
                .columns(List.of(column))
                .table(table)
                .build();

        assertThat(builder.toCheckExpression(constraint, CheckOperation.BETWEEN, List.of("a", "b")))
                .isEqualTo(expected);
    }

    @Test
    void test_returnsCorrectExpression_forColumnIsIn_check_onNumericTypes() {
        String expected = "CHECK (\"test_column\" IN (1, 2, 3))";

        column.setType(DataType.BIGINT);
        TableConstraint constraint = TableConstraint.builder()
                .type(TableConstraintType.CHECK)
                .columns(List.of(column))
                .table(table)
                .build();

        assertThat(builder.toCheckExpression(constraint, CheckOperation.IN, List.of("1", "2", "3")))
                .isEqualTo(expected);
    }

    @Test
    void test_returnsCorrectExpression_forColumnIsIn_check_onTextTypes() {
        String expected = "CHECK (\"test_column\" IN ('a', 'b', 'c'))";

        TableConstraint constraint = TableConstraint.builder()
                .type(TableConstraintType.CHECK)
                .columns(List.of(column))
                .table(table)
                .build();

        assertThat(builder.toCheckExpression(constraint, CheckOperation.IN, List.of("a", "b", "c")))
                .isEqualTo(expected);
    }
}
