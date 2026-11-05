package io.github.anastasifade.dbms.table.validation.annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTableConstraintColumnCount {
}
