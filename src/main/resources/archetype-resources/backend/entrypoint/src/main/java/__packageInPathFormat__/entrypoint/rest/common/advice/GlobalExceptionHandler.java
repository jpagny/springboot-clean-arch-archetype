package ${package}.entrypoint.rest.common.advice;

import ${package}.domain.commons.exceptions.engine.BaseBusinessException;
import ${package}.presentation.common.errors.DefaultErrorResponse;
import ${package}.presentation.common.errors.http.ErrorCodeToHttpStatusResolver;
import ${package}.presentation.common.i18n.BusinessErrorMessageResolver;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String TRACE_ID_KEY = "X-Flow-Id";

    private final BusinessErrorMessageResolver businessErrorMessageResolver;
    private final ErrorCodeToHttpStatusResolver httpStatusResolver;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        var priority = java.util.List.of("NotBlank", "NotNull", "Size", "Pattern");
        var perField = new java.util.LinkedHashMap<String, String>();

        ex.getBindingResult().getFieldErrors().forEach(fe -> {
            var field = fe.getField();
            var code  = fe.getCode(); // "NotBlank", "Size"...
            var msg   = fe.getDefaultMessage();
            if (!perField.containsKey(field)) {
                perField.put(field, code + "|" + msg);
                return;
            }
            var current = perField.get(field).split("\\|", 2)[0];
            int newP = priority.indexOf(code);
            int oldP = priority.indexOf(current);
            if ((oldP < 0 && newP >= 0) || (newP >= 0 && newP < oldP)) {
                perField.put(field, code + "|" + msg);
            }
        });

        var message = perField.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue().split("\\|", 2)[1])
                .collect(java.util.stream.Collectors.joining("; "));

        var status = HttpStatus.BAD_REQUEST;
        var body = DefaultErrorResponse.of(
                "VALIDATION_ERROR",
                message,
                status.value(),
                request.getRequestURI(),
                MDC.get(TRACE_ID_KEY)
        );
        return ResponseEntity.status(status).body(body);
    }


    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<DefaultErrorResponse> handleBaseBusinessException(BaseBusinessException ex,
                                                                            HttpServletRequest request) {
        var locale = request.getLocale() != null ? request.getLocale() : Locale.getDefault();
        var traceId = MDC.get(TRACE_ID_KEY);
        var path = request.getRequestURI();

        var status = httpStatusResolver.resolve(ex.getCode());
        var localized = businessErrorMessageResolver.resolve(ex.getBusinessError(), locale, ex.getArgs());

        var body = DefaultErrorResponse.of(ex.getCode().name(), localized, status.value(), path, traceId);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception", ex);

        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var traceId = MDC.get(TRACE_ID_KEY);
        var path = request.getRequestURI();

        var body = DefaultErrorResponse.of(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                status.value(),
                path,
                traceId
        );
        return ResponseEntity.status(status).body(body);
    }
}
