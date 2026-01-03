package ${package}.domain.commons.exceptions.engine;

import ${package}.domain.commons.exceptions.codes.ErrorCode;

/**
 * Contract for business error definitions.
 *
 * <p>
 * This interface represents a domain-level business error definition.
 * It is typically implemented by enums or classes that describe
 * specific business error cases.
 * </p>
 *
 * <p>
 * A business error is associated with a generic {@link ErrorCode},
 * allowing the Domain layer to classify errors while remaining
 * independent from technical concerns such as transport protocols
 * or frameworks.
 * </p>
 */
public interface IBusinessError {

    /**
     * Returns the generic error code associated with this business error.
     *
     * @return the {@link ErrorCode}
     */
    ErrorCode code();
}
