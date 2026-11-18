package io.github.anastasifade.dbms.table.repository;

import io.github.anastasifade.dbms.table.model.ColumnMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<ColumnMetadata, Long> {
}
