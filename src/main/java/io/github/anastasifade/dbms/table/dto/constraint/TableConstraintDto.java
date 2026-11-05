package io.github.anastasifade.dbms.table.dto.constraint;

import java.util.Set;

public interface TableConstraintDto {
    Set<String> getColumns();

    ConstraintDto getConstraint();
}
