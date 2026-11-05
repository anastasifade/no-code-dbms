package io.github.anastasifade.dbms.table.model;

import io.github.anastasifade.dbms.table.enums.constraint.ColumnConstraintType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "column_constraints", schema = "metadata")
@Getter
@Setter
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ColumnConstraint {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ColumnConstraintType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id", nullable = false)
    private UserColumn column;

    @Column(name = "default_value")
    private String defaultValue;

    @Column
    private Integer length;

    @Column
    private Integer precision;

    @Column
    private Integer scale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnConstraint that)) return false;
        return this.getId() != null && that.getId() != null &&
                Objects.equals(this.getId(), that.getId()) &&
                type == that.type &&
                this.getColumn().getId() != null &&
                that.getColumn().getId() != null &&
                Objects.equals(this.getColumn().getId(), that.getColumn().getId()) &&
                Objects.equals(defaultValue, that.defaultValue) &&
                Objects.equals(length, that.length) &&
                Objects.equals(precision, that.precision) &&
                Objects.equals(scale, that.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, defaultValue, length, precision, scale);
    }

    @Override
    public String toString() {
        return "ColumnConstraint{" +
                "id=" + (id == null ? "NULL" : id) +
                ", type=" + type.getValue() +
                ", column_id=" + (column.getId() == null ? "NULL" : column.getId()) +
                (defaultValue == null ? "" : ", defaultValue='" + defaultValue + '\'') +
                (length == null ? "" : ", length=" + length) +
                (precision == null ? "" : ", precision=" + precision) +
                (scale == null ? "" : ", scale=" + scale) +
                '}';
    }
}
