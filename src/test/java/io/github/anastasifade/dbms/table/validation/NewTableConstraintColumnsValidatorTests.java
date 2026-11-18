package io.github.anastasifade.dbms.table.validation;

import io.github.anastasifade.dbms.table.dto.column.NewColumnDto;
import io.github.anastasifade.dbms.table.dto.constraint.NewTableConstraintDto;
import io.github.anastasifade.dbms.table.dto.constraint.UniqueConstraintDto;
import io.github.anastasifade.dbms.table.dto.table.NewTableDto;
import io.github.anastasifade.dbms.table.enums.DataType;
import io.github.anastasifade.dbms.table.validation.validators.NewTableConstraintColumnsValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewTableConstraintColumnsValidatorTests {

    private static final ConstraintValidatorContext CONTEXT = null;

    private final NewTableConstraintColumnsValidator validator = new NewTableConstraintColumnsValidator();

    @Test
    void test_returnsTrue_ifAllConstraintColumns_existInTable() {
        String columnName = "test";

        NewTableConstraintDto constraint = getTableConstraintDtoForColumns(Set.of(columnName));
        NewColumnDto column = getColumnWithName(columnName);
        NewTableDto table = getTableWithConstraintAndColumns(Set.of(constraint), List.of(column));

        assertTrue(validator.isValid(table, CONTEXT));
    }

    @Test
    void test_returnsFalse_forConstraintColumn_sameNameDifferentCase_asTableColumn() {
        String columnName = "test";

        NewTableConstraintDto constraint = getTableConstraintDtoForColumns(Set.of(columnName.toUpperCase()));
        NewColumnDto column = getColumnWithName(columnName);
        NewTableDto table = getTableWithConstraintAndColumns(Set.of(constraint), List.of(column));

        assertFalse(validator.isValid(table, CONTEXT));
    }

    @Test
    void test_returnsTrue_ifAllConstraintColumns_exist_andOtherColumnsAlsoExistInTable() {
        String columnA = "test_a";
        String columnB = "test_b";
        String columnC = "test_c";

        NewTableConstraintDto constraint = getTableConstraintDtoForColumns(Set.of(columnA, columnB));
        List<NewColumnDto> columns = List.of(
                getColumnWithName(columnA),
                getColumnWithName(columnB),
                getColumnWithName(columnC)
        );
        NewTableDto table = getTableWithConstraintAndColumns(Set.of(constraint), columns);

        assertTrue(validator.isValid(table, CONTEXT));
    }

    @Test
    void test_returnsTrue_ifSome_butNotAll_constraintColumnsExistInTable() {
        String columnA = "test_a";
        String columnB = "test_b";
        String columnC = "test_c";

        NewTableConstraintDto constraint = getTableConstraintDtoForColumns(Set.of(columnA, columnB, columnC));
        List<NewColumnDto> columns = List.of(
                getColumnWithName(columnA),
                getColumnWithName(columnB)
        );
        NewTableDto table = getTableWithConstraintAndColumns(Set.of(constraint), columns);

        assertFalse(validator.isValid(table, CONTEXT));
    }

    @Test
    void test_returnsTrue_ifTableConstraintSet_isNull() {
        String columnName = "test";

        NewColumnDto column = getColumnWithName(columnName);
        NewTableDto table = getTableWithConstraintAndColumns(null, List.of(column));

        assertTrue(validator.isValid(table, CONTEXT));
    }

    @Test
    void test_returnsTrue_ifTableConstraintSet_isEmpty() {
        String columnName = "test";

        NewColumnDto column = getColumnWithName(columnName);
        NewTableDto table = getTableWithConstraintAndColumns(Set.of(), List.of(column));

        assertTrue(validator.isValid(table, CONTEXT));
    }

    @Test
    void test_returnsTrue_ifTableConstraintSetIsEmpty_andTableColumnsListIsEmpty() {
        NewTableDto table = getTableWithConstraintAndColumns(Set.of(), List.of());
        assertTrue(validator.isValid(table, CONTEXT));
    }

    @Test
    void test_returnsFalse_ifTableConstraintsExist_andTableColumnsList_isNull() {
        NewTableConstraintDto constraint = getTableConstraintDtoForColumns(Set.of("test"));
        NewTableDto table = getTableWithConstraintAndColumns(Set.of(constraint), null);
        assertFalse(validator.isValid(table, CONTEXT));
    }

    @Test
    void test_returnsFalse_ifTableConstraintsExist_andTableColumnsList_isEmpty() {
        NewTableConstraintDto constraint = getTableConstraintDtoForColumns(Set.of("test"));
        NewTableDto table = getTableWithConstraintAndColumns(Set.of(constraint), List.of());
        assertFalse(validator.isValid(table, CONTEXT));
    }


    private NewTableConstraintDto getTableConstraintDtoForColumns(Set<String> columns) {
        return NewTableConstraintDto.builder()
                .constraint(new UniqueConstraintDto())
                .columns(columns)
                .build();
    }

    private NewColumnDto getColumnWithName(String name) {
        return NewColumnDto.builder()
                .name(name)
                .type(DataType.VARCHAR)
                .build();
    }

    private NewTableDto getTableWithConstraintAndColumns(Set<NewTableConstraintDto> constraints,
                                                         List<NewColumnDto> columns) {
        return NewTableDto.builder()
                .name("TEST")
                .columns(columns)
                .constraints(constraints)
                .build();
    }
}
