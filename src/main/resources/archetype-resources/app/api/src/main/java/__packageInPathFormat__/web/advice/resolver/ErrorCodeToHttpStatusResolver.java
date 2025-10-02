package ${package}.web.advice.resolver;

import ${package}.domain.commons.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public interface ErrorCodeToHttpStatusResolver {
    HttpStatus resolve(ErrorCode code);
}
