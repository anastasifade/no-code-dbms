package io.github.anastasifade.dbms.table.validation;

import io.github.anastasifade.dbms.table.dto.constraint.*;
import io.github.anastasifade.dbms.table.validation.validators.TableConstraintColumnCountValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TableConstraintColumnCountValidatorTests {

    private static final ConstraintValidatorContext CONTEXT = null;

    private final TableConstraintColumnCountValidator validator = new TableConstraintColumnCountValidator();

    @Test
    void test_returnsTrue_forOneColumn_onNotNullConstraint() {
        NewTableConstraintDto constraint = getTableConstraintSingleColumn(new NotNullConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsFalse_forMultipleColumns_onNotNullConstraint() {
        NewTableConstraintDto constraint = getTableConstraintMultipleColumns(new NotNullConstraintDto());
        assertFalse(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forOneColumn_onDefaultConstraint() {
        NewTableConstraintDto constraint = getTableConstraintSingleColumn(new DefaultConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsFalse_forMultipleColumns_onDefaultConstraint() {
        NewTableConstraintDto constraint = getTableConstraintMultipleColumns(new DefaultConstraintDto());
        assertFalse(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forOneColumn_onLengthConstraint() {
        NewTableConstraintDto constraint = getTableConstraintSingleColumn(new LengthConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsFalse_forMultipleColumns_onLengthConstraint() {
        NewTableConstraintDto constraint = getTableConstraintMultipleColumns(new LengthConstraintDto());
        assertFalse(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forOneColumn_onPrecisionScaleConstraint() {
        NewTableConstraintDto constraint = getTableConstraintSingleColumn(new PrecisionScaleConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsFalse_forMultipleColumns_onPrecisionScaleConstraint() {
        NewTableConstraintDto constraint = getTableConstraintMultipleColumns(new PrecisionScaleConstraintDto());
        assertFalse(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forOneColumn_onPrimaryKeyConstraint() {
        NewTableConstraintDto constraint = getTableConstraintSingleColumn(new PrimaryKeyConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forMultipleColumns_onPrimaryKeyConstraint() {
        NewTableConstraintDto constraint = getTableConstraintMultipleColumns(new PrimaryKeyConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forOneColumn_onUniqueConstraint() {
        NewTableConstraintDto constraint = getTableConstraintSingleColumn(new UniqueConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forMultipleColumns_onUniqueConstraint() {
        NewTableConstraintDto constraint = getTableConstraintMultipleColumns(new UniqueConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forOneColumn_onCheckConstraint() {
        NewTableConstraintDto constraint = getTableConstraintSingleColumn(new CheckConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsFalse_forMultipleColumns_onCheckConstraint() {
        NewTableConstraintDto constraint = getTableConstraintMultipleColumns(new CheckConstraintDto());
        assertFalse(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forOneColumn_onForeignKeyConstraint() {
        NewTableConstraintDto constraint = getTableConstraintSingleColumn(new ForeignKeyConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forMultipleColumns_onForeignKeyConstraint() {
        NewTableConstraintDto constraint = getTableConstraintMultipleColumns(new ForeignKeyConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forOneColumn_onUnknownConstraint() {
        NewTableConstraintDto constraint = getTableConstraintSingleColumn(new UnknownConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    @Test
    void test_returnsTrue_forMultipleColumns_onUnknownConstraint() {
        NewTableConstraintDto constraint = getTableConstraintMultipleColumns(new UnknownConstraintDto());
        assertTrue(validator.isValid(constraint, CONTEXT));
    }

    private NewTableConstraintDto getTableConstraintSingleColumn(ConstraintDto constraint) {
        return NewTableConstraintDto.builder()
                .columns(Set.of("test"))
                .constraint(constraint)
                .build();
    }

    private NewTableConstraintDto getTableConstraintMultipleColumns(ConstraintDto constraint) {
        return NewTableConstraintDto.builder()
                .columns(Set.of("test_a", "test_b", "test_c"))
                .constraint(constraint)
                .build();
    }
}
