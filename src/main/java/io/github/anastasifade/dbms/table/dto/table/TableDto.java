package io.github.anastasifade.dbms.table.dto.table;

import io.github.anastasifade.dbms.table.dto.column.ColumnDto;
import io.github.anastasifade.dbms.table.dto.constraint.TableConstraintDto;

import java.util.Set;

public interface TableDto {
    String getName();

    Set<? extends ColumnDto> getColumns();

    Set<? extends TableConstraintDto> getConstraints();
}
