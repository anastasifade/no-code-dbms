package io.github.anastasifade.dbms.table.model;

import io.github.anastasifade.dbms.table.enums.DataType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "columns", schema = "metadata")
@Getter
@Setter
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserColumn {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 63)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 16)
    private DataType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private UserTable table;

    @ManyToMany
    @JoinTable(name = "table_constraint_columns",
            joinColumns = @JoinColumn(name = "column_id"),
            inverseJoinColumns = @JoinColumn(name = "table_constraint_id"))
    private Set<TableConstraint> constraints; // table-level constraints applied to the given column

    @ManyToMany
    @JoinTable(name = "table_constraints_ref_columns",
            joinColumns = @JoinColumn(name = "ref_column_id"),
            inverseJoinColumns = @JoinColumn(name = "table_constraint_id"))
    private Set<TableConstraint> references; // foreign key constraints that reference the given column

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserColumn column)) return false;
        return Objects.equals(name, column.name) &&
                Objects.equals(type, column.type) &&
                this.getTable().getId() != null &&
                column.getTable().getId() != null &&
                Objects.equals(this.getTable().getId(), column.getTable().getId());
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
                ", type='" + type.getSqlType() + '\'' +
                ", table=" + table +
                '}';
    }
}
