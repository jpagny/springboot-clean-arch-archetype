package ${package}.api.shared.resolver.error.http;

import ${package}.domain.commons.exceptions.codes.ErrorCode;
import org.springframework.http.HttpStatus;

public interface ErrorCodeToHttpStatusResolver {
    HttpStatus resolve(ErrorCode code);
}
