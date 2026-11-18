package io.github.anastasifade.dbms.table.dto.table;

import io.github.anastasifade.dbms.table.dto.column.ColumnDto;
import io.github.anastasifade.dbms.table.dto.constraint.TableConstraintDto;

import java.util.List;
import java.util.Set;

public interface TableDto {
    String getName();

    List<? extends ColumnDto> getColumns();

    Set<? extends TableConstraintDto> getConstraints();
}
