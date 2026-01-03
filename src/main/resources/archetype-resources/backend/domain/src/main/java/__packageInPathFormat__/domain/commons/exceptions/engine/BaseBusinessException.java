package ${package}.domain.commons.exceptions.engine;

import ${package}.domain.commons.exceptions.codes.ErrorCode;

import java.io.Serial;
import java.util.Arrays;

/**
 * Base runtime exception for business rule violations.
 *
 * <p>
 * This exception represents a domain-level business error and acts as the
 * foundation for all business exceptions in the system.
 * </p>
 *
 * <p>
 * It wraps an {@link IBusinessError}, which defines a stable business error
 * contract including an {@link ErrorCode}. This allows the Domain layer
 * to remain framework-agnostic while enabling consistent error handling
 * and mapping in outer layers (application, infrastructure, APIs).
 * </p>
 */
public class BaseBusinessException extends RuntimeException implements IBaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Business error definition associated with this exception.
     */
    private final IBusinessError businessError;

    /**
     * Optional arguments used to parameterize error messages.
     */
    private final Object[] args;

    /**
     * Creates a new business exception for the given business error.
     *
     * @param businessError the business error definition
     * @param args optional arguments used for message formatting or i18n
     */
    public BaseBusinessException(IBusinessError businessError, Object... args) {
        super(businessError.code().name());
        this.businessError = businessError;
        this.args = (args == null ? new Object[0] : Arrays.copyOf(args, args.length));
    }

    /**
     * Returns the generic error code associated with this exception.
     *
     * @return the {@link ErrorCode}
     */
    @Override
    public ErrorCode getCode() {
        return businessError.code();
    }

    /**
     * Returns the arguments associated with this exception.
     *
     * @return a defensive copy of the arguments array
     */
    @Override
    public Object[] getArgs() {
        return Arrays.copyOf(args, args.length);
    }

    /**
     * Returns the business error definition.
     *
     * @return the {@link IBusinessError}
     */
    @Override
    public IBusinessError getBusinessError() {
        return businessError;
    }

    /**
     * Returns a stable business error identifier.
     *
     * <p>
     * If the business error is an {@link Enum}, its name is used.
     * Otherwise, the simple class name is returned.
     * </p>
     *
     * @return the business error identifier
     */
    @Override
    public String getBusinessCode() {
        if (businessError instanceof Enum<?> e) {
            return e.name();
        }
        return businessError.getClass().getSimpleName();
    }

}
