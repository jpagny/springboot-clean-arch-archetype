package ${package}.transport.common.errors.http;

import ${package}.domain.commons.exceptions.codes.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Contract for resolving HTTP status codes from domain error codes.
 *
 * <p>
 * This interface defines a mapping strategy between domain-level
 * {@link ErrorCode} values and transport-level {@link HttpStatus} codes.
 * </p>
 *
 * <p>
 * It belongs to the transport layer and acts as an adaptation boundary,
 * ensuring that the Domain layer remains independent from HTTP semantics.
 * </p>
 *
 * <p>
 * Implementations are free to define custom mappings depending on
 * application needs, API conventions, or standards.
 * </p>
 */
public interface ErrorCodeToHttpStatusResolver {

    /**
     * Resolves the HTTP status corresponding to the given domain error code.
     *
     * @param code the domain error code
     * @return the resolved {@link HttpStatus}
     */
    HttpStatus resolve(ErrorCode code);
}
