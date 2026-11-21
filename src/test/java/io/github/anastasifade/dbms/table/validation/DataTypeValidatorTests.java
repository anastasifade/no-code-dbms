package io.github.anastasifade.dbms.table.validation;

import io.github.anastasifade.dbms.table.enums.DataType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static io.github.anastasifade.dbms.table.validation.business.DataTypeValidator.isValid;

public class DataTypeValidatorTests {

    @Test
    void test_varchar_returnsTrueForString() {
        assertTrue(isValid("abcdefg", DataType.VARCHAR));
    }

    @Test
    void test_varchar_returnsTrueForNumbersOnlyString() {
        assertTrue(isValid("123456", DataType.VARCHAR));
    }

    @Test
    void test_varchar_returnsFalseForBlankString() {
        assertFalse(isValid("      ", DataType.VARCHAR));
    }

    @Test
    void test_boolean_returnsTrue_forBooleanValueTrue() {
        assertTrue(isValid("true", DataType.BOOLEAN));
    }

    @Test
    void test_boolean_returnsTrue_forBooleanValueTrue_ignoresCase() {
        assertTrue(isValid("tRuE", DataType.BOOLEAN));
    }

    @Test
    void test_boolean_returnsTrue_forBooleanValueFalse() {
        assertTrue(isValid("false", DataType.BOOLEAN));
    }

    @Test
    void test_boolean_returnsTrue_forBooleanValueFalse_ignoresCase() {
        assertTrue(isValid("FaLsE", DataType.BOOLEAN));
    }

    @Test
    void test_boolean_returnsFalse_forInvalidBooleanValue() {
        assertFalse(isValid("yes", DataType.BOOLEAN));
    }

    @Test
    void test_smallInt_returnsTrue_forValidInput() {
        assertTrue(isValid("1024", DataType.SMALLINT));
    }

    @Test
    void test_smallInt_returnsFalse_forInputOutOfRange() {
        assertFalse(isValid("38457", DataType.SMALLINT));
    }

    @Test
    void test_smallInt_returnsFalse_forNonNumericInput() {
        assertFalse(isValid("3abc67", DataType.SMALLINT));
    }

    @Test
    void test_int_returnsTrue_forValidInput() {
        assertTrue(isValid("-123456", DataType.INTEGER));
    }

    @Test
    void test_int_returnsFalse_forInputOutOfRange() {
        assertFalse(isValid("3456728114", DataType.INTEGER));
    }

    @Test
    void test_int_returnsFalse_forNonNumericInput() {
        assertFalse(isValid("3abc138z", DataType.INTEGER));
    }

    @Test
    void test_bigint_returnsTrue_forValidInput() {
        assertTrue(isValid("4000000000", DataType.BIGINT));
    }

    @Test
    void test_bigint_returnsFalse_forInputOutOfRange() {
        assertFalse(isValid("99999999999999999999999999999999999999999", DataType.BIGINT));
    }

    @Test
    void test_bigint_returnsFalse_forNonNumericInput() {
        assertFalse(isValid("3427abc138z", DataType.BIGINT));
    }

    @Test
    void test_numeric_returnsTrue_forValidInput() {
        assertTrue(isValid("4567.9985", DataType.NUMERIC));
    }

    @Test
    void test_numeric_returnsFalse_forNonNumericInput() {
        assertFalse(isValid("3,2a", DataType.NUMERIC));
    }

    @Test
    void test_float_returnsTrue_forValidInput() {
        assertTrue(isValid("400.59", DataType.FLOAT));
    }

    @Test
    void test_float_returnsFalse_forNonNumericInput() {
        assertFalse(isValid("3427abc138z", DataType.FLOAT));
    }


    @Test
    void test_double_returnsTrue_forValidInput() {
        assertTrue(isValid("400.590007548", DataType.DOUBLE));
    }

    @Test
    void test_double_returnsFalse_forNonNumericInput() {
        assertFalse(isValid("3427abc138z", DataType.DOUBLE));
    }

    @Test
    void test_date_returnsTrue_forValidDate() {
        assertTrue(isValid("1900-01-01", DataType.DATE));
    }

    @Test
    void test_date_returnsFalse_forDate_nonIsoFormat() {
        assertFalse(isValid("01.01.1900", DataType.DATE));
    }

    @Test
    void test_time_returnsTrue_forValidTime() {
        assertTrue(isValid("00:00:00", DataType.TIME));
    }

    @Test
    void test_time_returnsFalse_forTime_nonIsoFormat() {
        assertFalse(isValid("00-00-00", DataType.TIME));
    }

    @Test
    void test_timeTz_returnsTrue_forValidTimeTz() {
        assertTrue(isValid("00:00:00+03:00", DataType.TIME_WITH_TIMEZONE));
    }

    @Test
    void test_timeTz_returnsFalse_forTimeTz_nonIsoFormat() {
        assertFalse(isValid("00-00-00 UTC+3", DataType.TIME_WITH_TIMEZONE));
    }

    @Test
    void test_timestamp_returnsTrue_forValidTime() {
        assertTrue(isValid("1900-01-01T00:00:00", DataType.TIMESTAMP));
    }

    @Test
    void test_timestamp_returnsFalse_forTime_nonIsoFormat() {
        assertFalse(isValid("01.01.1900T00:00:00", DataType.TIMESTAMP));
    }

    @Test
    void test_timestampTz_returnsTrue_forValidTime() {
        assertTrue(isValid("1900-01-01T00:00:00+03:00", DataType.TIMESTAMP_WITH_TIMEZONE));
    }

    @Test
    void test_timestampTz_returnsFalse_forTime_nonIsoFormat() {
        assertFalse(isValid("01.01.1900T00:00:00 UTC+3", DataType.TIMESTAMP_WITH_TIMEZONE));
    }

}
