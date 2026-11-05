package io.github.anastasifade.dbms.table.model;

import io.github.anastasifade.dbms.table.enums.constraint.TableConstraintType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

/**
 * Represents constraints applied at table level. <br>
 * Every constraint type that can be applied to more than one column is treated as a table-level constraint. <br>
 * Represented constraint types include: [PRIMARY KEY, FOREIGN KEY, UNIQUE, CHECK].
 */

@Entity
@Table(name = "table_constraints", schema = "metadata")
@Getter
@Setter
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TableConstraint {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TableConstraintType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private UserTable table;

    /**
     * Columns that the given table-level constraint applies to.
     */
    @ManyToMany
    @JoinTable(name = "table_constraint_columns",
            joinColumns = @JoinColumn(name = "table_constraint_id"),
            inverseJoinColumns = @JoinColumn(name = "column_id"))
    private Set<UserColumn> columns;

    /**
     * (Optional) Only applicable to Check type constraints. Null in all other constraint types.
     */
    @Column(name = "check_expression")
    private String checkExpression;

    /**
     * (Optional) Only applicable to Foreign Key type constraints. Null in all other constraint types.
     * Represents the table referenced by the given Foreign Key.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_table")
    private UserTable referenceTable;

    /**
     * (Optional) Only applicable to Foreign Key type constraints. Nullable in all other constraint types.
     * Represents the columns referenced by the given Foreign Key.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "table_constraints_ref_columns",
            joinColumns = @JoinColumn(name = "table_constraint_id"),
            inverseJoinColumns = @JoinColumn(name = "ref_column_id"))
    private Set<UserColumn> referenceColumns;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableConstraint that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "TableConstraint{" +
                "id=" + (id == null ? "NULL" : id) +
                ", name='" + name + '\'' +
                ", type=" + type.toString() +
                ", table=" + getTable().getId() +
                (checkExpression == null ? "" : (", checkExpression='" + checkExpression + '\'')) +
                (getReferenceTable() == null ? "" :
                        referenceTable.getId() == null ? "" : (", referenceTable=" + getReferenceTable().getId())) +
                (referenceColumns == null ? "" : (", referenceColumns=" + referenceColumns)) +
                '}';
    }
}
