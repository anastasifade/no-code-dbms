package io.github.anastasifade.dbms.table.dto.constraint;

import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class NotNullConstraintDto implements ConstraintDto {
    @Override
    public DtoConstraintType getType() {
        return DtoConstraintType.NOT_NULL_CONSTRAINT;
    }
}
