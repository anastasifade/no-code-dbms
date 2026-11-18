package io.github.anastasifade.dbms.table.repository;

import io.github.anastasifade.dbms.table.model.ColumnConstraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnConstraintRepository extends JpaRepository<ColumnConstraint, Long> {
}
