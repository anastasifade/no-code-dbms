package io.github.anastasifade.dbms.table.dto.constraint;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.anastasifade.dbms.table.enums.constraint.DtoConstraintType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        defaultImpl = UnknownConstraintDto.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CheckConstraintDto.class, name = "CHECK_CONSTRAINT"),
        @JsonSubTypes.Type(value = DefaultConstraintDto.class, name = "DEFAULT_CONSTRAINT"),
        @JsonSubTypes.Type(value = ForeignKeyConstraintDto.class, name = "FOREIGN_KEY_CONSTRAINT"),
        @JsonSubTypes.Type(value = LengthConstraintDto.class, name = "LENGTH_CONSTRAINT"),
        @JsonSubTypes.Type(value = NotNullConstraintDto.class, name = "NOT_NULL_CONSTRAINT"),
        @JsonSubTypes.Type(value = PrecisionScaleConstraintDto.class, name = "PRECISION_AND_SCALE_CONSTRAINT"),
        @JsonSubTypes.Type(value = PrimaryKeyConstraintDto.class, name = "PRIMARY_KEY_CONSTRAINT"),
        @JsonSubTypes.Type(value = UniqueConstraintDto.class, name = "UNIQUE_CONSTRAINT"),
        @JsonSubTypes.Type(value = UnknownConstraintDto.class, name = "UNKNOWN")
})
public interface ConstraintDto {
    DtoConstraintType getType();
}
