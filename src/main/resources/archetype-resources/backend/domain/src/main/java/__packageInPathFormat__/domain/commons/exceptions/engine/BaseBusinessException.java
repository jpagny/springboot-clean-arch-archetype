package ${package}.domain.commons.exceptions.engine;

import ${package}.domain.commons.exceptions.codes.ErrorCode;

import java.io.Serial;
import java.util.Arrays;

public class BaseBusinessException extends RuntimeException implements IBaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final IBusinessError businessError;
    private final Object[] args;

    public BaseBusinessException(IBusinessError businessError, Object... args) {
        super(businessError.code().name());
        this.businessError = businessError;
        this.args = (args == null ? new Object[0] : Arrays.copyOf(args, args.length));
    }

    @Override
    public ErrorCode getCode() {
        return businessError.code();
    }

    @Override
    public Object[] getArgs() {
        return Arrays.copyOf(args, args.length);
    }

    @Override
    public IBusinessError getBusinessError() {
        return businessError;
    }

}
