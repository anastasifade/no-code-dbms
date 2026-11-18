package io.github.anastasifade.dbms.table.repository;

import io.github.anastasifade.dbms.table.model.TableConstraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableConstraintRepository extends JpaRepository<TableConstraint, Long> {
}
