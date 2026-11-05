package io.github.anastasifade.dbms.table.dto.constraint;

import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Default implementation of polymorphic ConstraintDto to handle unknown constraint types.
 */
@EqualsAndHashCode
@ToString
public final class UnknownConstraintDto implements ConstraintDto {
    @Override
    public DtoConstraintType getType() {
        return DtoConstraintType.UNKNOWN;
    }
}
