package io.github.anastasifade.dbms.table.validation;

import io.github.anastasifade.dbms.table.dto.constraint.PrecisionScaleConstraintDto;
import io.github.anastasifade.dbms.table.validation.validators.PrecisionScaleValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static io.github.anastasifade.dbms.common.constants.PostgreSqlLimits.MAX_PRECISION;
import static io.github.anastasifade.dbms.common.constants.PostgreSqlLimits.MAX_SCALE;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrecisionScaleValidatorTests {

    private static final ConstraintValidatorContext CONTEXT = null;

    private final PrecisionScaleValidator validator = new PrecisionScaleValidator();

    // ---- testing valid behavior ----

    @Test
    void test_returnsTrue_whenScaleIsLessThanPrecision() {
        assertTrue(validator.isValid(new PrecisionScaleConstraintDto(10, 1), CONTEXT));
    }

    @Test
    void test_returnsTrue_whenScaleIsNull() {
        assertTrue(validator.isValid(new PrecisionScaleConstraintDto(10, null), CONTEXT));
    }

    @Test
    void test_returnsFalse_whenScaleIsGreaterThanPrecision() {
        assertFalse(validator.isValid(new PrecisionScaleConstraintDto(10, 1000), CONTEXT));
    }

    // ---- testing invalid states that should be caught by other annotations ----

    @Test
    void test_returnsTrue_whenPrecisionIsNull() {
        assertTrue(validator.isValid(new PrecisionScaleConstraintDto(null, 1), CONTEXT));
    }

    @Test
    void test_returnsTrue_whenPrecisionIsZero() {
        assertTrue(validator.isValid(new PrecisionScaleConstraintDto(0, 1), CONTEXT));
    }

    @Test
    void test_returnsTrue_whenPrecisionIsNegative() {
        assertTrue(validator.isValid(new PrecisionScaleConstraintDto(-10, 1), CONTEXT));
    }

    @Test
    void test_returnsTrue_whenPrecisionIsOverMaxLimit() {
        assertTrue(validator.isValid(new PrecisionScaleConstraintDto(MAX_PRECISION + 1, 1), CONTEXT));
    }

    @Test
    void test_returnsTrue_whenScaleIsNegative() {
        assertTrue(validator.isValid(new PrecisionScaleConstraintDto(10, -10), CONTEXT));
    }

    @Test
    void test_returnsTrue_whenScaleIsOverMaxLimit() {
        assertTrue(validator.isValid(new PrecisionScaleConstraintDto(10, MAX_SCALE + 1), CONTEXT));
    }
}
