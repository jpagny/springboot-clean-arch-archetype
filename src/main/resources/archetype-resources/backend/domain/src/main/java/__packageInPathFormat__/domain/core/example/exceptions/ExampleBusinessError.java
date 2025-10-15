package ${package}.domain.core.example.exceptions;

import ${package}.domain.commons.exceptions.codes.ErrorCode;
import ${package}.domain.commons.exceptions.engine.ErrorContext;
import ${package}.domain.commons.exceptions.engine.IBusinessError;

@ErrorContext("example")
public enum ExampleBusinessError implements IBusinessError {

    INVALID_NAME(ErrorCode.INVALID_VALUE),
    ALREADY_EXISTS(ErrorCode.CONFLICT),
    FORBIDDEN_NAME(ErrorCode.FORBIDDEN),
    NOT_FOUND(ErrorCode.NOT_FOUND);

    private final ErrorCode code;

    ExampleBusinessError(ErrorCode code) {
        this.code = code;
    }
    @Override public ErrorCode code() { return code; }
}