package ${package}.transport.common.errors.http.impl;

import ${package}.transport.common.errors.http.ErrorCodeToHttpStatusResolver;
import ${package}.domain.commons.exceptions.codes.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class DefaultErrorCodeToHttpStatusResolverImpl implements ErrorCodeToHttpStatusResolver {

    private static final Map<ErrorCode, HttpStatus> MAP = new EnumMap<>(ErrorCode.class);

    static {
        MAP.put(ErrorCode.FORBIDDEN, HttpStatus.FORBIDDEN);
        MAP.put(ErrorCode.CONFLICT, HttpStatus.CONFLICT);
        MAP.put(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        MAP.put(ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        MAP.put(ErrorCode.INVALID_VALUE, HttpStatus.BAD_REQUEST);
        MAP.put(ErrorCode.MISSING_REQUIRED_FIELD, HttpStatus.BAD_REQUEST);
        MAP.put(ErrorCode.EMPTY_VALUE, HttpStatus.BAD_REQUEST);
    }

    public DefaultErrorCodeToHttpStatusResolverImpl() {
    }

    @Override
    public HttpStatus resolve(ErrorCode code) {
        if (code == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return MAP.getOrDefault(code, HttpStatus.BAD_REQUEST);
    }
}
