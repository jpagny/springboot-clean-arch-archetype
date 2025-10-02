package ${package}.web.advice;

import ${package}.web.dto.DefaultErrorResponse;
import ${package}.web.advice.resolver.ErrorCodeToHttpStatusResolver;
import ${package}.domain.commons.exceptions.BaseBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorCodeToHttpStatusResolver httpStatusResolver;

    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<DefaultErrorResponse> handleBaseBusinessException(BaseBusinessException ex) {
        HttpStatus status = httpStatusResolver.resolve(ex.getCode());
        DefaultErrorResponse body = DefaultErrorResponse.of(ex.getCode().name(), ex.getMessage());
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultErrorResponse> handleUnexpectedException(Exception ex) {
        DefaultErrorResponse body = DefaultErrorResponse.of(ex.getClass().getName(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
