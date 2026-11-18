package io.github.anastasifade.dbms.table.model;

import io.github.anastasifade.dbms.table.enums.constraint.ColumnConstraintType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Represents constraints applied at column level. <br>
 * Every constraint type that can be applied to only one column at a time is treated as a column-level constraint. <br>
 * Represented constraint types include: {@code NOT NULL}, {@code DEFAULT}, as well as the two domain constraints
 * that affect column data types without mapping directly to SQL constraints: <br>
 * - {@code LENGTH} - defines the maximum length for {@code VARCHAR} data type; <br>
 * - {@code PRECISION AND SCALE} - defines precision and scale of numeric data types.
 */

@Entity
@Table(name = "column_constraints", schema = "metadata")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnConstraint {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ColumnConstraintType type;

    /**
     * Column affected by the given constraint.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id", nullable = false)
    private ColumnMetadata column;

    /**
     * (Optional) Default value, only applicable to {@code DEFAULT} constraints.
     */
    @Column(name = "default_value")
    private String defaultValue;

    /**
     * (Optional) Maximum length for {@code VARCHAR} data type. Only applicable to {@code LENGTH} constraints.
     */
    @Column
    private Integer length;

    /**
     * (Optional) Precision of a numeric data type. Only applicable to {@code PRECISION AND SCALE} constraint.
     */
    @Column
    private Integer precision;

    /**
     * (Optional) Scale of a numeric data type. Only applicable to {@code PRECISION AND SCALE} constraint.
     * If null with a non-null precision, defaults to 0.
     */
    @Column
    private Integer scale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnConstraint that)) return false;
        if (getId() != null && that.getId() != null) {
            return getId().equals(that.getId());
        }
        if (getColumn() != null && that.getColumn() != null &&
                getColumn().getId() != null && that.getColumn().getId() != null) {
            return getColumn().getId().equals(that.getColumn().getId()) &&
                    getType().equals(that.getType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "ColumnConstraint{" +
                "id=" + (id == null ? "NULL" : id) +
                ", type=" + type.getSql() +
                ", column_id=" + (column.getId() == null ? "NULL" : column.getId()) +
                (defaultValue == null ? "" : ", defaultValue='" + defaultValue + '\'') +
                (length == null ? "" : ", length=" + length) +
                (precision == null ? "" : ", precision=" + precision) +
                (scale == null ? "" : ", scale=" + scale) +
                '}';
    }
}
