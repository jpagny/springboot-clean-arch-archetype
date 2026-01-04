package ${package}.transport.common.errors.http.impl;

import ${package}.transport.common.errors.http.ErrorCodeToHttpStatusResolver;
import ${package}.domain.commons.exceptions.codes.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * Default implementation of {@link ErrorCodeToHttpStatusResolver}.
 *
 * <p>
 * This component provides a predefined mapping between domain-level
 * {@link ErrorCode} values and HTTP {@link HttpStatus} codes.
 * </p>
 *
 * <p>
 * It belongs to the transport layer and ensures that domain error codes
 * are translated into appropriate HTTP semantics without introducing
 * HTTP dependencies into the domain.
 * </p>
 *
 * <p>
 * Unmapped error codes are resolved to {@link HttpStatus#BAD_REQUEST}
 * by default. A {@code null} error code results in
 * {@link HttpStatus#INTERNAL_SERVER_ERROR}.
 * </p>
 */
@Component
public class DefaultErrorCodeToHttpStatusResolverImpl
        implements ErrorCodeToHttpStatusResolver {

    /**
     * Static mapping between domain error codes and HTTP status codes.
     */
    private static final Map<ErrorCode, HttpStatus> MAP =
            new EnumMap<>(ErrorCode.class);

    static {
        MAP.put(ErrorCode.FORBIDDEN, HttpStatus.FORBIDDEN);
        MAP.put(ErrorCode.CONFLICT, HttpStatus.CONFLICT);
        MAP.put(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        MAP.put(ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        MAP.put(ErrorCode.INVALID_VALUE, HttpStatus.BAD_REQUEST);
        MAP.put(ErrorCode.MISSING_REQUIRED_FIELD, HttpStatus.BAD_REQUEST);
        MAP.put(ErrorCode.EMPTY_VALUE, HttpStatus.BAD_REQUEST);
    }

    /**
     * Resolves the HTTP status corresponding to the given domain error code.
     *
     * @param code the domain error code
     * @return the resolved {@link HttpStatus}
     */
    @Override
    public HttpStatus resolve(ErrorCode code) {
        if (code == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return MAP.getOrDefault(code, HttpStatus.BAD_REQUEST);
    }
}
