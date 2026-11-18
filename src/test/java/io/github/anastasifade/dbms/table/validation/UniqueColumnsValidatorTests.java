package io.github.anastasifade.dbms.table.validation;

import io.github.anastasifade.dbms.table.dto.column.ColumnDto;
import io.github.anastasifade.dbms.table.dto.column.NewColumnDto;
import io.github.anastasifade.dbms.table.enums.DataType;
import io.github.anastasifade.dbms.table.validation.validators.UniqueColumnsValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UniqueColumnsValidatorTests {

    private static final ConstraintValidatorContext CONTEXT = null;

    private final UniqueColumnsValidator validator = new UniqueColumnsValidator();

    @Test
    void test_returnsTrue_forOneColumn() {
        List<ColumnDto> columns = List.of(
                NewColumnDto.builder()
                        .name("a")
                        .type(DataType.VARCHAR)
                        .build()
        );

        assertTrue(validator.isValid(columns, CONTEXT));
    }

    @Test
    void test_returnsTrue_forUniqueColumnNames() {
        List<ColumnDto> columns = List.of(
                NewColumnDto.builder()
                        .name("a")
                        .type(DataType.VARCHAR)
                        .build(),
                NewColumnDto.builder()
                        .name("b")
                        .type(DataType.VARCHAR)
                        .build(),
                NewColumnDto.builder()
                        .name("c")
                        .type(DataType.VARCHAR)
                        .build()
        );

        assertTrue(validator.isValid(columns, CONTEXT));
    }

    @Test
    void test_unique_isCaseSensitive() {
        List<ColumnDto> columns = List.of(
                NewColumnDto.builder()
                        .name("a")
                        .type(DataType.VARCHAR)
                        .build(),
                NewColumnDto.builder()
                        .name("A")
                        .type(DataType.VARCHAR)
                        .build()
        );

        assertTrue(validator.isValid(columns, CONTEXT));
    }

    @Test
    void test_returnsFalse_ifColumnNamesNotUnique() {
        List<ColumnDto> columns = List.of(
                NewColumnDto.builder()
                        .name("a")
                        .type(DataType.VARCHAR)
                        .build(),
                NewColumnDto.builder()
                        .name("a")
                        .type(DataType.NUMERIC)
                        .build()
        );

        assertFalse(validator.isValid(columns, CONTEXT));
    }

    @Test
    void test_returnsFalse_ifSomeColumnNames_areNotUnique() {
        List<ColumnDto> columns = List.of(
                NewColumnDto.builder()
                        .name("a")
                        .type(DataType.VARCHAR)
                        .build(),
                NewColumnDto.builder()
                        .name("b")
                        .type(DataType.VARCHAR)
                        .build(),
                NewColumnDto.builder()
                        .name("a")
                        .type(DataType.NUMERIC)
                        .build()
        );

        assertFalse(validator.isValid(columns, CONTEXT));
    }

    @Test
    void test_returnsTrue_onEmptySet() {
        assertTrue(validator.isValid(List.of(), CONTEXT));
    }
}
