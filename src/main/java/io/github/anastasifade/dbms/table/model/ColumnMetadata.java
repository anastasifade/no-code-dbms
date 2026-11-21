package io.github.anastasifade.dbms.table.model;

import io.github.anastasifade.dbms.table.enums.DataType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the metadata of a column within a user-defined table.
 * <p>
 * Describes the name of the column, its data type and its position within the table,
 * as well as stores a reference to the table it belongs to.
 * <p>
 * For convenient access, Lists of table-level ({@link TableConstraint}) and column-level ({@link ColumnConstraint})
 * constraints, as well as a List of foreign key constrains ({@link TableConstraint}) pointing to the given column
 * are also added as fields. These relationships are owned and mapped by the constraints.
 */

@Entity
@Table(name = "columns", schema = "metadata")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnMetadata {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Column name, unique within a table, case-sensitive.
     */
    @Column(nullable = false, length = 63)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 16)
    private DataType type;

    /**
     * Index of the column within the table, used to preserve the order of the columns as defined by the user. <br>
     * Column indexing starts from 0.
     */
    @Column(nullable = false)
    private Integer index;

    /**
     * Table within which the given column is defined.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private TableMetadata table;

    /**
     * List of column-level constraints applied to the given column.
     */
    @OneToMany(mappedBy = "column", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ColumnConstraint> columnConstraints = new ArrayList<>();

    /**
     * List of table-level constraints that affect the given column.
     */
    @ManyToMany(mappedBy = "columns", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @Builder.Default
    private List<TableConstraint> tableConstraints = new ArrayList<>();

    /**
     * List of {@code FOREIGN KEY} constraints that point to the given column.
     */
    @ManyToMany(mappedBy = "referenceColumns",  cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @Builder.Default
    private List<TableConstraint> references = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnMetadata column)) return false;
        if (getId() != null && column.getId() != null) {
            return getId().equals(column.getId());
        }
        if (getTable() != null && getTable().getId() != null &&
        column.getTable() != null && column.getTable().getId() != null) {
            return getName().equals(column.getName()) &&
                    getTable().getId().equals(column.getTable().getId()) &&
                    getType().equals(column.getType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "Column{" +
                "id=" + (id == null ? "NULL" : id) +
                ", name='" + name + '\'' +
                ", type='" + type.getSql() + '\'' +
                ", table=" + (table == null ? "NULL" : table.getId()) +
                '}';
    }
}
