package io.github.anastasifade.dbms.table.serialization;

import io.github.anastasifade.dbms.table.dto.column.NewColumnDto;
import io.github.anastasifade.dbms.table.dto.constraint.*;
import io.github.anastasifade.dbms.table.dto.table.NewTableDto;
import io.github.anastasifade.dbms.table.enums.constraint.CheckOperation;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DeserializationTests {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void test_correctlyDeserializes_newTableDto_noConstraints() {
        String json = """
                {
                  "name": "TEST_TABLE",
                  "columns": [ { "name": "TEST_COLUMN", "type": "VARCHAR" } ]
                }
                """;

        NewTableDto dto = mapper.readValue(json, NewTableDto.class);

        assertThat(dto.getConstraints()).isNullOrEmpty();
        assertThat(dto.getColumns())
                .hasSize(1);

        assertThat(dto.getColumns().stream().findFirst().get().getConstraints()).isNullOrEmpty();
    }

    @Test
    void test_correctlyDeserializes_newTableDto_withTableConstraint() {
        String json = """
                {
                  "name": "TEST_TABLE",
                  "columns": [ { "name": "TEST_COLUMN", "type": "VARCHAR" } ],
                  "constraints": [
                    {
                      "columns": [ "TEST_COLUMN" ],
                      "constraint": { "type": "UNIQUE_CONSTRAINT" }
                    }
                  ]
                }
                """;

        NewTableDto dto = mapper.readValue(json, NewTableDto.class);
        assertThat(dto.getConstraints())
                .hasSize(1);
        assertThat(dto.getConstraints()
                .stream()
                .findFirst()
                .get()
                .getConstraint())
                .isInstanceOf(UniqueConstraintDto.class);
    }

    @Test
    void test_correctlyDeserializes_newTableDto_withMultipleTableConstraints() {
        String json = """
                {
                  "name": "TEST_TABLE",
                  "columns": [ { "name": "TEST_COLUMN", "type": "VARCHAR" } ],
                  "constraints": [
                    {
                      "columns": [ "TEST_COLUMN" ],
                      "constraint": { "type": "UNIQUE_CONSTRAINT" }
                    },
                    {
                      "columns": [ "TEST_COLUMN" ],
                      "constraint": { "type": "NOT_NULL_CONSTRAINT" }
                    }
                  ]
                }
                """;

        NewTableDto dto = mapper.readValue(json, NewTableDto.class);

        assertThat(dto.getConstraints()
                .stream()
                .map(NewTableConstraintDto::getConstraint)
                .toList())
                .containsExactlyInAnyOrder(new UniqueConstraintDto(), new NotNullConstraintDto());
    }

    @Test
    void test_correctlyDeserializes_columnDto_withMultipleConstraints() {
        String json = """
                {
                  "name": "TEST_COLUMN",
                  "type": "VARCHAR",
                  "constraints": [
                    { "type": "DEFAULT_CONSTRAINT", "defaultValue": "TEST_VALUE" },
                    { "type": "LENGTH_CONSTRAINT", "length": 16 },
                    { "type": "CHECK_CONSTRAINT", "checkOperation": "NOT_EQUALS", "checkValues": [ "test" ] },
                    { "type": "PRIMARY_KEY_CONSTRAINT" },
                    { "type": "FOREIGN_KEY_CONSTRAINT", "referencedTable": "TEST_TABLE", "referencedColumns": [ "T" ] },
                    { "type": "PRECISION_AND_SCALE_CONSTRAINT", "precision": 12 }
                  ]
                }
                """;

        NewColumnDto dto = mapper.readValue(json, NewColumnDto.class);

        assertThat(dto.getConstraints())
                .containsExactlyInAnyOrder(new DefaultConstraintDto("TEST_VALUE"),
                        new LengthConstraintDto(16),
                        new CheckConstraintDto(CheckOperation.NOT_EQUALS, List.of("test")),
                        new PrimaryKeyConstraintDto(),
                        new ForeignKeyConstraintDto("TEST_TABLE", Set.of("T")),
                        new PrecisionScaleConstraintDto(12, null));
    }

    @Test
    void test_unknownConstraint_deserializesToUnknownConstraintDto_withoutThrowing() {
        String json = """
                {
                  "name": "TEST_COLUMN",
                  "type": "VARCHAR",
                  "constraints": [
                    { "type": "NOT_A_TYPE", "not_a_field": "TEST_VALUE" }
                  ]
                }
                """;

        NewColumnDto dto = mapper.readValue(json, NewColumnDto.class);

        assertThat(dto.getConstraints())
                .containsExactly(new UnknownConstraintDto());
    }
}
