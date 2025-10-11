package ${package}.api.shared.resolver.error.http.impl;

import ${package}.api.shared.resolver.error.http.ErrorCodeToHttpStatusResolver;
import ${package}.domain.commons.exceptions.codes.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

@Component
public final class ErrorCodeToHttpStatusResolverImpl implements ErrorCodeToHttpStatusResolver {

    private static final Map<ErrorCode, HttpStatus> MAPPINGS;

    static {
        var m = new EnumMap<ErrorCode, HttpStatus>(ErrorCode.class);
        m.put(ErrorCode.INVALID_VALUE, HttpStatus.NOT_FOUND);
        MAPPINGS = Collections.unmodifiableMap(m);
    }

    @Override
    public HttpStatus resolve(ErrorCode code) {
        return code == null
                ? HttpStatus.INTERNAL_SERVER_ERROR
                : MAPPINGS.getOrDefault(code, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}