package io.github.anastasifade.dbms.table.dto.column;

import io.github.anastasifade.dbms.table.dto.constraint.ConstraintDto;
import io.github.anastasifade.dbms.table.enums.DataType;

import java.util.Set;

public interface ColumnDto {
    String getName();

    DataType getType();

    Set<ConstraintDto> getConstraints();
}
