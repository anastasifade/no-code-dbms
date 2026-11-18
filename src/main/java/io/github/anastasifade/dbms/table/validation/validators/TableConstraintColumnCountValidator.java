package io.github.anastasifade.dbms.table.validation.validators;

import io.github.anastasifade.dbms.table.dto.constraint.TableConstraintDto;
import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;
import io.github.anastasifade.dbms.table.validation.annotations.ValidTableConstraintColumnCount;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TableConstraintColumnCountValidator
        implements ConstraintValidator<ValidTableConstraintColumnCount, TableConstraintDto> {
    @Override
    public boolean isValid(TableConstraintDto value, ConstraintValidatorContext context) {
        DtoConstraintType constraintType = value.getConstraint().getType();
        int columnCount = value.getColumns().size();

        return switch (constraintType) {
            case UNKNOWN -> true;
            case NOT_NULL_CONSTRAINT, DEFAULT_CONSTRAINT, LENGTH_CONSTRAINT, PRECISION_AND_SCALE_CONSTRAINT,
                 CHECK_CONSTRAINT -> columnCount == 1;
            case PRIMARY_KEY_CONSTRAINT, UNIQUE_CONSTRAINT, FOREIGN_KEY_CONSTRAINT -> columnCount > 0;
        };
    }
}
