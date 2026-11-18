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
        // constraints cannot be invalid if there are none
        if (dto.getConstraints() == null || dto.getConstraints().isEmpty()) return true;

        // if constraints exist but no columns have been defined, the constraints are invalid
        if (dto.getColumns() == null || dto.getColumns().isEmpty()) return false;

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