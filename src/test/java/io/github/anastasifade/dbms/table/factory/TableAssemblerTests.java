package io.github.anastasifade.dbms.table.factory;

import io.github.anastasifade.dbms.table.dto.column.NewColumnDto;
import io.github.anastasifade.dbms.table.dto.constraint.CheckConstraintDto;
import io.github.anastasifade.dbms.table.dto.constraint.LengthConstraintDto;
import io.github.anastasifade.dbms.table.dto.constraint.NewTableConstraintDto;
import io.github.anastasifade.dbms.table.dto.constraint.UniqueConstraintDto;
import io.github.anastasifade.dbms.table.dto.table.NewTableDto;
import io.github.anastasifade.dbms.table.enums.DataType;
import io.github.anastasifade.dbms.table.enums.constraint.CheckOperation;
import io.github.anastasifade.dbms.table.enums.constraint.ColumnConstraintType;
import io.github.anastasifade.dbms.table.enums.constraint.TableConstraintType;
import io.github.anastasifade.dbms.table.model.TableMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TableAssemblerTests {

    private final TableAssembler assembler =
            new TableAssembler(new TableConstraintNameGenerator(), new CheckExpressionBuilder());

    private NewTableDto dto;

    @BeforeEach
    void setUp() {
        List<NewColumnDto> columns = List.of(
                NewColumnDto.builder()
                        .name("test_column_a")
                        .type(DataType.VARCHAR)
                        .build(),
                NewColumnDto.builder()
                        .name("test_column_b")
                        .type(DataType.BIGINT)
                        .build()
        );

        dto = NewTableDto.builder()
                .name("test_table")
                .columns(columns)
                .build();
    }

    @Test
    void test_assemblesValidTableMetadata_withoutConstraints() {
        TableMetadata table = assembler.getTableMetadata(dto);
        assertThat(table).satisfies(t -> {
            assertThat(t.getName()).isEqualTo(dto.getName());
            assertThat(table.getColumns().size()).isEqualTo(dto.getColumns().size());
            assertThat(table.getTableConstraints()).isEmpty();
            assertThat(t.getColumns()).satisfies(columns -> {
                assertThat(columns.getFirst().getIndex()).isEqualTo(0);
                assertThat(columns.getFirst().getName()).isEqualTo(dto.getColumns().getFirst().getName());
                assertThat(columns.getFirst().getTable()).isSameAs(t);
                assertThat(columns.get(1).getIndex()).isEqualTo(1);
            });
        });
    }

    @Test
    void test_trimsTableAndColumnNames_beforeMapping() {
        String tableName = "   test table   ";
        String firstColumnName = "   first column   ";
        dto.setName(tableName);
        dto.getColumns().getFirst().setName(firstColumnName);

        TableMetadata table = assembler.getTableMetadata(dto);
        assertThat(table).satisfies(t -> {
            assertThat(t.getName()).isEqualTo("test table");
            assertThat(t.getColumns().getFirst().getName()).isEqualTo("first column");
        });
    }

    @Test
    void test_correctlyMaps_andEnriches_tableConstraints() {
        NewTableConstraintDto tableConstraintDto = NewTableConstraintDto.builder()
                .constraint(new CheckConstraintDto(CheckOperation.IS_NOT_NULL, null))
                .columns(Set.of(dto.getColumns().getFirst().getName()))
                .build();

        dto.setConstraints(Set.of(tableConstraintDto));

        TableMetadata table = assembler.getTableMetadata(dto);
        assertThat(table.getTableConstraints()).satisfies(constraints -> {
            assertThat(constraints.size()).isEqualTo(1);
            assertThat(constraints.getFirst().getName()).isNotBlank();
            assertThat(constraints.getFirst().getType()).isEqualTo(TableConstraintType.CHECK);
            assertThat(constraints.getFirst().getCheckExpression()).isNotBlank();
            assertThat(constraints.getFirst().getTable()).isSameAs(table);
            assertThat(constraints.getFirst().getColumns().getFirst()).isSameAs(table.getColumns().getFirst());
        });
    }

    @Test
    void test_correctlyMapsTableConstraints_fromConstraintDto_onColumnDto() {
        dto.getColumns()
                .getFirst()
                .setConstraints(Set.of(new UniqueConstraintDto()));

        TableMetadata table = assembler.getTableMetadata(dto);
        assertThat(table).satisfies(t -> {
            assertThat(t.getTableConstraints().size()).isEqualTo(1);
            assertThat(t.getTableConstraints().getFirst()).satisfies(constraint -> {
                assertThat(constraint.getType()).isEqualTo(TableConstraintType.UNIQUE);
                assertThat(constraint.getName()).isNotBlank();
                assertThat(constraint.getCheckExpression()).isNull();
                assertThat(constraint.getTable()).isSameAs(table);
                assertThat(constraint.getColumns().getFirst()).isSameAs(table.getColumns().getFirst());
            });
            assertThat(t.getColumns().getFirst().getColumnConstraints()).isEmpty();
        });
    }

    @Test
    void test_correctlyMapsColumnConstraints() {
        dto.getColumns()
                .getFirst()
                .setConstraints(Set.of(new LengthConstraintDto(6)));

        TableMetadata table = assembler.getTableMetadata(dto);
        assertThat(table).satisfies(t -> {
            assertThat(t.getTableConstraints()).isEmpty();
            assertThat(t.getColumns().getFirst()).satisfies(column -> {
                assertThat(column.getColumnConstraints().size()).isEqualTo(1);
                assertThat(column.getColumnConstraints().getFirst()).satisfies(constraint -> {
                    assertThat(constraint.getType()).isEqualTo(ColumnConstraintType.LENGTH);
                    assertThat(constraint.getLength()).isEqualTo(6);
                    assertThat(constraint.getDefaultValue()).isNull();
                    assertThat(constraint.getPrecision()).isNull();
                    assertThat(constraint.getScale()).isNull();
                    assertThat(constraint.getColumn()).isSameAs(column);
                });
            });
        });
    }
}
