package io.github.anastasifade.dbms.table.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "tables", schema = "metadata")
@Getter
@Setter
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class UserTable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, length = 63)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTable table)) return false;
        return name.equalsIgnoreCase(table.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
