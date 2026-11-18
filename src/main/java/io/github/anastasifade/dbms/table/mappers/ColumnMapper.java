package io.github.anastasifade.dbms.table.mappers;

import io.github.anastasifade.dbms.table.dto.column.NewColumnDto;
import io.github.anastasifade.dbms.table.model.ColumnMetadata;
import io.github.anastasifade.dbms.table.model.TableMetadata;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.IntStream;

@UtilityClass
public class ColumnMapper {

    /**
     *
     * Method for batch mapping of table column dtos to {@link ColumnMetadata} entities.
     * Calls {@link #toColumn(NewColumnDto dto, TableMetadata table, int index)} for each {@link NewColumnDto} in the list,
     * passing the order within the list as the index.
     * @param dtos list of valid {@link NewColumnDto}, sorted in the order of column position within the table,
     *             from first to last.
     * @param table pre-mapped {@link TableMetadata}.
     * @return List of {@link ColumnMetadata} with assigned indices, table references, types and normalized (trimmed) names.
     * <p>
     * Does not assign ids, map constraints or assign foreign key references to given columns.
     */
    public List<ColumnMetadata> toColumns(List<NewColumnDto> dtos, TableMetadata table) {
        return IntStream.range(0, dtos.size())
                .mapToObj(idx -> toColumn(dtos.get(idx), table, idx))
                .toList();
    }

    /**
     *
     * @param dto valid {@link NewColumnDto}
     * @param table pre-mapped {@link TableMetadata}
     * @param index non-negative index indicating the position of the column within the given table
     * @return {@link ColumnMetadata} instance with index, table reference, type, and a normalized (trimmed) name.
     * <p>
     * Does not assign id, map constraints or assign foreign key references to the given column.
     */
    public ColumnMetadata toColumn(NewColumnDto dto, TableMetadata table, int index) {
        return ColumnMetadata.builder()
                .name(dto.getName().trim())
                .type(dto.getType())
                .table(table)
                .index(index)
                .build();
    }

}
