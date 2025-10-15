package ${package}.domain.core.example.exceptions;

import ${package}.domain.commons.exceptions.engine.BaseBusinessException;

public class ExampleDomainException extends BaseBusinessException {

    private ExampleDomainException(ExampleBusinessError error, Object... args) {
        super(error, args);
    }

    public static ExampleDomainException invalidName(Object... args) {
        return new ExampleDomainException(ExampleBusinessError.INVALID_NAME, args);
    }

    public static ExampleDomainException alreadyExists(Object... args) {
        return new ExampleDomainException(ExampleBusinessError.ALREADY_EXISTS, args);
    }

    public static ExampleDomainException forbiddenName() {
        return new ExampleDomainException(ExampleBusinessError.FORBIDDEN_NAME);
    }

    public static ExampleDomainException notFound(Object... args) {
        return new ExampleDomainException(ExampleBusinessError.NOT_FOUND, args);
    }
}
