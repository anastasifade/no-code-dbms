package io.github.anastasifade.dbms.exception;

public class UnexpectedConstraintType extends RuntimeException {
    public UnexpectedConstraintType(String message) {
        super(message);
    }
}
