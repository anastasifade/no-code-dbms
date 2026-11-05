package io.github.anastasifade.dbms.table.validation.validators;

import io.github.anastasifade.dbms.table.validation.annotations.UniqueCaseSensitive;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;

public class UniqueCaseSensitiveElementsValidator implements
        ConstraintValidator<UniqueCaseSensitive, Set<String>> {

    @Override
    public boolean isValid(Set<String> values, ConstraintValidatorContext context) {
        Set<String> uqValues = values.stream()
                .map(String::trim)
                .collect(Collectors.toSet());
        return uqValues.size() == values.size();
    }
}
