package io.github.anastasifade.dbms.table.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the metadata of a user-defined table that has been created in the {@code user_data} schema.
 * <p>
 * Describes the name of the table, unique per schema.
 * <p>
 * For convenient access, a list of columns defined within the table ({@link ColumnMetadata}) and
 * a list of table-level constraints ({@link TableConstraint}) affecting the given table have been added.
 * These relationships are owned and mapped by the columns and the constraints, respectively.
 */

@Entity
@Table(name = "tables", schema = "metadata")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableMetadata {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Table name. Unique per schema, case-sensitive: quoted identifiers are used within the user-defined schema
     * to prevent SQL injection and permit case-sensitivity for the identifiers used.
     */
    @Column(nullable = false, unique = true, length = 63)
    private String name;

    /**
     * List of columns defined within the given table, ordered by their position within the table.
     */
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("index ASC")
    @Builder.Default
    private List<ColumnMetadata> columns = new ArrayList<>();

    /**
     * List of table-level constraints applied to the given table.
     */
    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TableConstraint> tableConstraints = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableMetadata table)) return false;
        return getName().equals(table.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "TableMetadata{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
