package io.github.anastasifade.dbms.table.factory;

import io.github.anastasifade.dbms.table.dto.column.NewColumnDto;
import io.github.anastasifade.dbms.table.dto.constraint.CheckConstraintDto;
import io.github.anastasifade.dbms.table.dto.constraint.ConstraintDto;
import io.github.anastasifade.dbms.table.dto.constraint.NewTableConstraintDto;
import io.github.anastasifade.dbms.table.dto.table.NewTableDto;
import io.github.anastasifade.dbms.table.enums.constraint.TableConstraintType;
import io.github.anastasifade.dbms.table.mappers.ColumnMapper;
import io.github.anastasifade.dbms.table.mappers.ConstraintMapper;
import io.github.anastasifade.dbms.table.mappers.TableMapper;
import io.github.anastasifade.dbms.table.model.ColumnConstraint;
import io.github.anastasifade.dbms.table.model.ColumnMetadata;
import io.github.anastasifade.dbms.table.model.TableConstraint;
import io.github.anastasifade.dbms.table.model.TableMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Table assembler handles the mapping of table DTOs and the bidirectional relationships between tables, columns
 * and constraints.
 * <p>
 * The component maps DTOs into domain models in a hierarchical order: table -> columns -> table constraints ->
 * column constraints. It then sets the relationships in reverse order.
 * <p>
 * Table assembler also handles basic enrichment of table-level constraint by calling
 * {@link TableConstraintNameGenerator} to generate a unique constraint name, and by calling
 * {@link CheckExpressionBuilder} to generate a String representation of a CHECK expression for every
 * table constraint with {@link TableConstraintType}{@code .CHECK}.
 **/
@Component
@RequiredArgsConstructor
public class TableAssembler {

    private final TableConstraintNameGenerator tableConstraintNameGenerator;
    private final CheckExpressionBuilder checkExpressionBuilder;

    public TableMetadata getTableMetadata(NewTableDto dto) {
        TableMetadata table = TableMapper.toTable(dto);
        addColumns(table, dto.getColumns());
        addConstraints(table, dto);
        return table;
    }

    private void addColumns(TableMetadata table, List<NewColumnDto> columnDtos) {
        List<ColumnMetadata> columns = ColumnMapper.toColumns(columnDtos, table);
        table.setColumns(columns);
    }

    private void addConstraints(TableMetadata table, NewTableDto dto) {
        if (table.getColumns() == null || table.getColumns().isEmpty()) return;

        Map<String, ColumnMetadata> columnsByName = table.getColumns()
                .stream()
                .collect(Collectors.toMap(ColumnMetadata::getName, Function.identity()));

        addTableConstraints(table, dto, columnsByName);
        addColumnConstraints(dto.getColumns(), columnsByName);
    }

    private void addColumnConstraints(List<NewColumnDto> columnDtos,
                                      Map<String, ColumnMetadata> columnsByName) {
        columnDtos.forEach(dto -> addColumnConstraintsForColumn(dto.getConstraints(),
                columnsByName.get(dto.getName().trim())));
    }

    private void addColumnConstraintsForColumn(Set<ConstraintDto> dtos, ColumnMetadata column) {
        if (dtos == null || dtos.isEmpty()) return;

        List<ColumnConstraint> constraints = dtos.stream()
                .filter(dto -> dto.getType().isColumnConstraint())
                .map(dto -> ConstraintMapper.toColumnConstraint(dto, column))
                .toList();
        column.setColumnConstraints(constraints);
    }

    private void addTableConstraints(TableMetadata table, NewTableDto dto, Map<String, ColumnMetadata> columnsByName) {
        List<TableConstraint> tableConstraints = new ArrayList<>();
        tableConstraints.addAll(mapTableConstraintDtos(dto, table, columnsByName));
        tableConstraints.addAll(mapColumnConstraintDtosToTableConstraints(dto.getColumns(), table, columnsByName));

        table.setTableConstraints(tableConstraints);
    }

    private List<TableConstraint> mapTableConstraintDtos(NewTableDto dto, TableMetadata table,
                                                        Map<String, ColumnMetadata> columnsByName) {
        if (dto.getConstraints() == null || dto.getConstraints().isEmpty()) return List.of();

        return dto.getConstraints()
                .stream()
                .map(constraintDto ->
                        toTableConstraint(constraintDto, table, columnsByName))
                .toList();
    }

    private List<TableConstraint> mapColumnConstraintDtosToTableConstraints(List<NewColumnDto> columnDtos,
                                                                              TableMetadata table,
                                                                              Map<String, ColumnMetadata> columns) {
        return columnDtos.stream()
                .flatMap(columnDto -> toTableConstraints(columnDto, table, columns).stream())
                .toList();
    }

    private List<TableConstraint> toTableConstraints(NewColumnDto columnDto, TableMetadata table,
                                                     Map<String, ColumnMetadata> columns) {
        if (columnDto.getConstraints() == null || columnDto.getConstraints().isEmpty()) return List.of();

        return columnDto.getConstraints().stream()
                .filter(constraint -> constraint.getType().isTableConstraint())
                .map(constraint ->
                        toTableConstraint(constraint, table, columns.get(columnDto.getName().trim())))
                .toList();
    }

    private TableConstraint toTableConstraint(NewTableConstraintDto dto, TableMetadata table,
                                              Map<String, ColumnMetadata> columns) {
        List<ColumnMetadata> columnsForConstraint = dto.getColumns()
                .stream()
                .map(col -> columns.get(col.trim()))
                .collect(Collectors.toCollection(ArrayList::new));
        TableConstraint constraint = ConstraintMapper.toTableConstraint(dto, columnsForConstraint, table);
        enrich(constraint, dto.getConstraint());
        return constraint;
    }

    private TableConstraint toTableConstraint(ConstraintDto dto, TableMetadata table, ColumnMetadata column) {
        TableConstraint constraint = ConstraintMapper.toTableConstraint(dto, column, table);
        enrich(constraint, dto);
        return constraint;
    }

    private void enrich(TableConstraint constraint, ConstraintDto dto) {
        setName(constraint);

        if (constraint.getType() == TableConstraintType.CHECK) {
            setCheckExpression(constraint, (CheckConstraintDto) dto);
        }
    }

    private void setName(TableConstraint constraint) {
        String name = tableConstraintNameGenerator.generateConstraintName(constraint);
        constraint.setName(name);
    }

    private void setCheckExpression(TableConstraint constraint, CheckConstraintDto checkConstraintDto) {
        String expression = checkExpressionBuilder.toCheckExpression(constraint,
                checkConstraintDto.getCheckOperation(),
                checkConstraintDto.getCheckValues());
        constraint.setCheckExpression(expression);
    }
}
