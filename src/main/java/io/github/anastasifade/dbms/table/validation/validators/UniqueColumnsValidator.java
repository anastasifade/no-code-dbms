package io.github.anastasifade.dbms.table.validation.validators;

import io.github.anastasifade.dbms.table.dto.column.ColumnDto;
import io.github.anastasifade.dbms.table.validation.annotations.UniqueColumns;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;

public class UniqueColumnsValidator implements ConstraintValidator<UniqueColumns, Set<ColumnDto>> {

    @Override
    public boolean isValid(Set<ColumnDto> values, ConstraintValidatorContext context) {
        Set<String> uqColumnNames = values.stream()
                .map(column -> column.getName().trim())
                .collect(Collectors.toSet());
        return values.size() == uqColumnNames.size();
    }
}
