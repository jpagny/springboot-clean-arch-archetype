package ${package}.entrypoint.rest.common.advice;

import ${package}.domain.commons.exceptions.engine.BaseBusinessException;
import ${package}.transport.common.errors.DefaultErrorResponse;
import ${package}.transport.common.errors.http.ErrorCodeToHttpStatusResolver;
import ${package}.transport.common.i18n.BusinessErrorMessageResolver;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String TRACE_ID_KEY = "X-Flow-Id";
    private static final String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";
    private static final String FIELD_PAYLOAD_SEPARATOR = "|";
    private static final String FIELD_PAYLOAD_SPLIT_REGEX = "\\|";

    private static final List<String> CONSTRAINT_PRIORITY = List.of("NotBlank", "NotNull", "Size", "Pattern");

    private final BusinessErrorMessageResolver businessErrorMessageResolver;
    private final ErrorCodeToHttpStatusResolver httpStatusResolver;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        var message = buildValidationMessage(ex);
        var traceId = MDC.get(TRACE_ID_KEY);
        var status  = HttpStatus.BAD_REQUEST;

        log.error("[VALIDATION_ERROR] path='{}' traceId='{}' -> {}", request.getRequestURI(), traceId, message);

        var body = DefaultErrorResponse.of(
                VALIDATION_ERROR_CODE,
                message,
                status.value(),
                request.getRequestURI(),
                traceId
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<DefaultErrorResponse> handleBaseBusinessException(BaseBusinessException ex,
                                                                            HttpServletRequest request) {
        var locale  = request.getLocale() != null ? request.getLocale() : Locale.getDefault();
        var traceId = MDC.get(TRACE_ID_KEY);
        var path    = request.getRequestURI();

        var status    = httpStatusResolver.resolve(ex.getCode());
        var localized = businessErrorMessageResolver.resolve(ex.getBusinessError(), locale, ex.getArgs());

        log.error("[BUSINESS_ERROR] code='{}' path='{}' traceId='{}' message='{}' args={}",
                ex.getCode(), path, traceId, localized, ex.getArgs(), ex);

        var body = DefaultErrorResponse.of(ex.getCode().name(), localized, status.value(), path, traceId);

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        var traceId = MDC.get(TRACE_ID_KEY);
        var path    = request.getRequestURI();
        var status  = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("[UNEXPECTED_ERROR] path='{}' traceId='{}' -> {}", path, traceId, ex.getMessage(), ex);

        var body = DefaultErrorResponse.of(
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                status.value(),
                path,
                traceId
        );

        return ResponseEntity.status(status).body(body);
    }

    private static String buildValidationMessage(MethodArgumentNotValidException ex) {
        Map<String, String> perField = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fe -> {
            var field = fe.getField();
            var code  = fe.getCode();
            var msg   = fe.getDefaultMessage();

            if (!perField.containsKey(field)) {
                perField.put(field, code + FIELD_PAYLOAD_SEPARATOR + msg);
                return;
            }

            var currentCode = perField.get(field).split(FIELD_PAYLOAD_SPLIT_REGEX, 2)[0];
            int newP = CONSTRAINT_PRIORITY.indexOf(code);
            int oldP = CONSTRAINT_PRIORITY.indexOf(currentCode);

            boolean shouldReplace = (oldP < 0 && newP >= 0) || (newP >= 0 && newP < oldP);
            if (shouldReplace) {
                perField.put(field, code + FIELD_PAYLOAD_SEPARATOR + msg);
            }
        });

        return perField.entrySet().stream()
                .map(e -> {
                    var parts = e.getValue().split(FIELD_PAYLOAD_SPLIT_REGEX, 2);
                    var message = (parts.length == 2 ? parts[1] : "");
                    return e.getKey() + ": " + message;
                })
                .collect(Collectors.joining("; "));
    }
}
