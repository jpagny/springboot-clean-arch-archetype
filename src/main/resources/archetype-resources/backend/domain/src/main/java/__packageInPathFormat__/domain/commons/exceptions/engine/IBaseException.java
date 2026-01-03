package ${package}.domain.commons.exceptions.engine;

import ${package}.domain.commons.exceptions.codes.ErrorCode;

/**
 * Base contract for domain-level business exceptions.
 *
 * <p>
 * This interface defines a common abstraction for all business exceptions
 * in the Domain layer. It exposes structured information that can be
 * interpreted and mapped by outer layers (application, infrastructure, APIs)
 * without leaking technical concerns into the domain.
 * </p>
 *
 * <p>
 * Implementations are expected to provide:
 * <ul>
 *   <li>A generic {@link ErrorCode} for classification</li>
 *   <li>A business-specific error definition</li>
 *   <li>A stable business error identifier</li>
 *   <li>Optional arguments for message formatting or localization</li>
 * </ul>
 * </p>
 */
public interface IBaseException {

    /**
     * Returns the generic error code associated with this exception.
     *
     * @return the {@link ErrorCode}
     */
    ErrorCode getCode();

    /**
     * Returns the business error definition associated with this exception.
     *
     * @return the {@link IBusinessError}
     */
    IBusinessError getBusinessError();

    /**
     * Returns a stable business error identifier.
     *
     * <p>
     * This value is intended to be used for error mapping, logging,
     * or client-facing error responses.
     * </p>
     *
     * @return the business error identifier
     */
    String getBusinessCode();

    /**
     * Returns the arguments associated with this exception.
     *
     * <p>
     * These arguments can be used for message formatting,
     * localization, or error enrichment.
     * </p>
     *
     * @return the error arguments
     */
    Object[] getArgs();
}
