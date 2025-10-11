package ${package}.domain.core.example.exceptions;

import ${package}.domain.commons.exceptions.codes.ErrorCode;
import ${package}.domain.commons.exceptions.engine.IBusinessError;

public enum ExampleBusinessError implements IBusinessError {

    EXAMPLE_ERROR(ErrorCode.EMPTY_VALUE);

    private final ErrorCode code;

    ExampleBusinessError(ErrorCode code) {
        this.code = code;
    }
    @Override public ErrorCode code() { return code; }
}