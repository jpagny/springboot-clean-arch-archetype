package ${package}.transport.common.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Default transport-level error response representation.
 *
 * <p>
 * This record represents a standardized error payload exposed by APIs
 * (REST, HTTP-based transports) when an error occurs.
 * </p>
 *
 * <p>
 * It is designed to be independent from the domain error model while
 * allowing a clear mapping from business exceptions to client-facing
 * error responses.
 * </p>
 *
 * <p>
 * Fields such as {@code code} and {@code businessCode} are typically derived
 * from domain-level {@code ErrorCode} and {@code BusinessError} definitions,
 * while transport-specific metadata (HTTP status, request path, trace ID)
 * is added at this layer.
 * </p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DefaultErrorResponse(

        /**
         * Generic error code (technical classification).
         */
        String code,

        /**
         * Business-specific error identifier.
         */
        String businessCode,

        /**
         * Human-readable error message.
         */
        String message,

        /**
         * Transport-level status (e.g. HTTP status code).
         */
        Integer status,

        /**
         * Request path that caused the error.
         */
        String path,

        /**
         * Correlation or trace identifier for observability.
         */
        String traceId,

        /**
         * Timestamp of the error occurrence.
         */
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Instant timestamp
) {

    /**
     * Creates a minimal error response.
     *
     * <p>
     * This factory method is typically used when transport-specific
     * metadata (status, path, trace ID) is not available.
     * </p>
     *
     * @param code the generic error code
     * @param businessCode the business-specific error identifier
     * @param message the error message
     * @return a {@link DefaultErrorResponse}
     */
    public static DefaultErrorResponse of(String code, String businessCode, String message) {
        return new DefaultErrorResponse(
                code,
                businessCode,
                message,
                null,
                null,
                null,
                Instant.now()
        );
    }

    /**
     * Creates a full error response with transport metadata.
     *
     * @param code the generic error code
     * @param businessCode the business-specific error identifier
     * @param message the error message
     * @param status the transport-level status (e.g. HTTP status)
     * @param path the request path
     * @param traceId the trace or correlation identifier
     * @return a {@link DefaultErrorResponse}
     */
    public static DefaultErrorResponse of(
            String code,
            String businessCode,
            String message,
            int status,
            String path,
            String traceId
    ) {
        return new DefaultErrorResponse(
                code,
                businessCode,
                message,
                status,
                path,
                traceId,
                Instant.now()
        );
    }
}
