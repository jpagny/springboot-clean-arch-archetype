package ${package}.web.advice.resolver.impl;

import ${package}.web.advice.resolver.ErrorCodeToHttpStatusResolver;
import ${package}.domain.commons.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

@Component
public final class ErrorCodeToHttpStatusResolverImpl implements ErrorCodeToHttpStatusResolver {

    private static final Map<ErrorCode, HttpStatus> MAPPINGS;

    static {
        EnumMap<ErrorCode, HttpStatus> m = new EnumMap<>(ErrorCode.class);
        m.put(ErrorCode.AN_ERROR_CODE, HttpStatus.NOT_FOUND);
        MAPPINGS = Collections.unmodifiableMap(m);
    }

    @Override
    public HttpStatus resolve(ErrorCode code) {
        return code == null
                ? HttpStatus.INTERNAL_SERVER_ERROR
                : MAPPINGS.getOrDefault(code, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
