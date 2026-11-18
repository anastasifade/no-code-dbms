package io.github.anastasifade.dbms.table.mappers;

import io.github.anastasifade.dbms.exception.UnexpectedConstraintType;
import io.github.anastasifade.dbms.table.dto.constraint.*;
import io.github.anastasifade.dbms.table.model.ColumnConstraint;
import io.github.anastasifade.dbms.table.model.ColumnMetadata;
import io.github.anastasifade.dbms.table.model.TableConstraint;
import io.github.anastasifade.dbms.table.model.TableMetadata;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;

/**
 * Mapper class for column-level and table-level constraints both. Due to overlap in DTOs
 * (i.e., a {@link ConstraintDto} may represent both a table- and a column-level constraint depending on the type),
 * the mapping is handled in the same class.
 */
@UtilityClass
public class ConstraintMapper {

    /**
     *
     * @param dto     valid {@link NewTableConstraintDto}
     * @param columns list of valid pre-mapped {@link ColumnMetadata}
     * @param table   valid pre-mapped {@link TableMetadata}
     * @return {@link TableConstraint} with a type, table reference, and columns references, sorted by their index
     * within the table in ascending order.
     * <p>
     * The mapper returns the {@link TableConstraint} with a {@code NULL} name.
     */
    public TableConstraint toTableConstraint(NewTableConstraintDto dto, List<ColumnMetadata> columns, TableMetadata table) {
        columns.sort(Comparator.comparingInt(ColumnMetadata::getIndex));
        return TableConstraint.builder()
                .columns(columns)
                .table(table)
                .type(dto.getConstraint().getType().getTableConstraintType())
                .build();
    }

    public TableConstraint toTableConstraint(ConstraintDto dto, ColumnMetadata column, TableMetadata table) {
        return TableConstraint.builder()
                .columns(List.of(column))
                .table(table)
                .type(dto.getType().getTableConstraintType())
                .build();
    }

    public ColumnConstraint toColumnConstraint(ConstraintDto dto, ColumnMetadata column) {
        ColumnConstraint constraint = ColumnConstraint.builder()
                .type(dto.getType().getColumnConstraintType())
                .column(column)
                .build();

        setValues(constraint, dto);
        return constraint;
    }

    private void setValues(ColumnConstraint constraint, ConstraintDto dto) {
        switch (dto) {
            case DefaultConstraintDto defaultConstraint ->
                    constraint.setDefaultValue(defaultConstraint.getDefaultValue());
            case LengthConstraintDto lengthConstraint -> constraint.setLength(lengthConstraint.getLength());
            case PrecisionScaleConstraintDto precisionScaleConstraint -> {
                constraint.setPrecision(precisionScaleConstraint.getPrecision());
                constraint.setScale(precisionScaleConstraint.getScale());
            }
            case NotNullConstraintDto ignored -> {
            }
            default ->
                    throw new UnexpectedConstraintType("Unexpected constraint type when mapping column constraint: " +
                            dto.getType());
        }
    }
}
