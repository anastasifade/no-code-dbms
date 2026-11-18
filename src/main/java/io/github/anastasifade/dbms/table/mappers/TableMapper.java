package io.github.anastasifade.dbms.table.mappers;

import io.github.anastasifade.dbms.table.dto.table.NewTableDto;
import io.github.anastasifade.dbms.table.model.TableMetadata;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TableMapper {

    /**
     *
     * @param dto valid NewTableDto.
     * @return TableMetadata with normalized (trimmed) table name.
     * <p>
     * Does not assign id, does not map columns or constraints.
     */
    public TableMetadata toTable(NewTableDto dto) {
        return TableMetadata.builder()
                .name(dto.getName().trim())
                .build();
    }
}
