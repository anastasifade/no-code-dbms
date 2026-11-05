package io.github.anastasifade.dbms.table.validation.validators;

import io.github.anastasifade.dbms.table.dto.table.NewTableDto;
import io.github.anastasifade.dbms.table.validation.annotations.ValidNewTableConstraintColumns;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;

public class NewTableConstraintColumnsValidator implements
        ConstraintValidator<ValidNewTableConstraintColumns, NewTableDto> {

    @Override
    public boolean isValid(NewTableDto dto, ConstraintValidatorContext cxt) {
        if (dto.getConstraints() == null || dto.getConstraints().isEmpty()) return true;

        Set<String> columnNames = dto.getColumns()
                .stream()
                .map(column -> column.getName().trim())
                .collect(Collectors.toSet());

        return dto.getConstraints()
                .stream()
                .allMatch(constraint -> constraint.getColumns()
                        .stream()
                        .allMatch(name -> columnNames.contains(name.trim())));
    }

}