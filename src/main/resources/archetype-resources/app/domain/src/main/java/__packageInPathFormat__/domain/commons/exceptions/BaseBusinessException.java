package ${package}.domain.commons.exceptions;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;

public class BaseBusinessException extends RuntimeException implements IBaseException {

    private static final long serialVersionUID = 1L;

    private final BusinessErrorCode businessError;
    private final Object[] args;

    public BaseBusinessException(BusinessErrorCode businessError, Object... args) {
        super(formatMessage(Objects.requireNonNull(businessError, "businessError must not be null"), args));
        this.businessError = businessError;
        this.args = (args == null ? new Object[0] : Arrays.copyOf(args, args.length));
    }

    public BaseBusinessException(BusinessErrorCode businessError, Throwable cause, Object... args) {
        super(formatMessage(Objects.requireNonNull(businessError, "businessError must not be null"), args), cause);
        this.businessError = businessError;
        this.args = (args == null ? new Object[0] : Arrays.copyOf(args, args.length));
    }

    @Override
    public ErrorCode getCode() {
        return businessError.getCode();
    }

    @Override
    public Object[] getArgs() {
        return Arrays.copyOf(args, args.length);
    }

    public BusinessErrorCode getBusinessError() {
        return businessError;
    }

    private static String formatMessage(BusinessErrorCode error, Object[] args) {
        String template = (error.getMessageTemplate() == null ? "" : error.getMessageTemplate());
        try {
            return MessageFormat.format(template, args == null ? new Object[0] : args);
        } catch (IllegalArgumentException e) {
            return template;
        }
    }
}
