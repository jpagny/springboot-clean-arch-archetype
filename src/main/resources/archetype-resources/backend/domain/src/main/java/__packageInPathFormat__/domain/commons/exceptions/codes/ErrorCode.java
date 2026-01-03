package ${package}.domain.commons.exceptions.codes;

/**
 * Enumeration of domain error codes.
 *
 * <p>
 * These error codes are used to classify domain and application-level
 * errors in a consistent and framework-agnostic way.
 * </p>
 *
 * <p>
 * Error codes are intended to be stable identifiers that can be mapped
 * to technical representations (HTTP status codes, messages, i18n keys,
 * logs) outside of the Domain layer.
 * </p>
 */
public enum ErrorCode {

    /**
     * Indicates that a required value is empty.
     */
    EMPTY_VALUE,

    /**
     * Indicates that a value does not respect validation rules
     * or domain constraints.
     */
    INVALID_VALUE,

    /**
     * Indicates that a required field is missing.
     */
    MISSING_REQUIRED_FIELD,

    /**
     * Indicates that the requested resource or model was not found.
     */
    NOT_FOUND,

    /**
     * Indicates a conflict with the current state of the system
     * or an existing model.
     */
    CONFLICT,

    /**
     * Indicates that the operation is not allowed due to
     * authorization or access rules.
     */
    FORBIDDEN,

    /**
     * Indicates an unexpected internal error.
     */
    INTERNAL_ERROR
}
