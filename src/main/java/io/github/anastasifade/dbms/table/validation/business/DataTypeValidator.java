package io.github.anastasifade.dbms.table.validation.business;

import io.github.anastasifade.dbms.table.enums.DataType;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.function.Predicate;

@UtilityClass
public class DataTypeValidator {

    private static final Predicate<String> VARCHAR_VALIDATOR = (s -> !s.isBlank());

    private static final Predicate<String> BOOLEAN_VALIDATOR =
            (s -> "true".equalsIgnoreCase(s) || "false".equalsIgnoreCase(s));

    private static final Predicate<String> SMALLINT_VALIDATOR = (s -> {
        try {
            Short.parseShort(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    });

    private static final Predicate<String> INTEGER_VALIDATOR = (s -> {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    });

    private static final Predicate<String> BIGINT_VALIDATOR = (s -> {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    });

    private static final Predicate<String> NUMERIC_VALIDATOR = (s -> {
        try {
            new BigDecimal(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    });

    private static final Predicate<String> FLOAT_VALIDATOR = (s -> {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    });

    private static final Predicate<String> DOUBLE_VALIDATOR = (s -> {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    });

    private static final Predicate<String> DATE_VALIDATOR = (s -> {
        try {
            LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    });

    private static final Predicate<String> TIME_VALIDATOR = (s -> {
        try {
            LocalTime.parse(s, DateTimeFormatter.ISO_LOCAL_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    });

    private static final Predicate<String> TIME_TZ_VALIDATOR = (s -> {
        try {
            OffsetTime.parse(s, DateTimeFormatter.ISO_OFFSET_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    });

    private static final Predicate<String> DATE_TIME_VALIDATOR = (s -> {
        try {
            LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    });

    private static final Predicate<String> DATE_TIME_TZ_VALIDATOR = (s -> {
        try {
            OffsetDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    });

    private static final Map<DataType, Predicate<String>> validators = Map.ofEntries(
            Map.entry(DataType.VARCHAR, VARCHAR_VALIDATOR),
            Map.entry(DataType.BOOLEAN, BOOLEAN_VALIDATOR),
            Map.entry(DataType.SMALLINT, SMALLINT_VALIDATOR),
            Map.entry(DataType.INTEGER, INTEGER_VALIDATOR),
            Map.entry(DataType.BIGINT, BIGINT_VALIDATOR),
            Map.entry(DataType.NUMERIC, NUMERIC_VALIDATOR),
            Map.entry(DataType.FLOAT, FLOAT_VALIDATOR),
            Map.entry(DataType.DOUBLE, DOUBLE_VALIDATOR),
            Map.entry(DataType.DATE, DATE_VALIDATOR),
            Map.entry(DataType.TIME, TIME_VALIDATOR),
            Map.entry(DataType.TIME_WITH_TIMEZONE, TIME_TZ_VALIDATOR),
            Map.entry(DataType.TIMESTAMP, DATE_TIME_VALIDATOR),
            Map.entry(DataType.TIMESTAMP_WITH_TIMEZONE, DATE_TIME_TZ_VALIDATOR)
    );

    public boolean isValid(String value, DataType type) {
        return validators.get(type).test(value);
    }

}
