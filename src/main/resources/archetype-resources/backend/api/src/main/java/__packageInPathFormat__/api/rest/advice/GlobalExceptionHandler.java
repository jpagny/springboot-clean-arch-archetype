package com.mycompany.api.rest.advice;

import com.mycompany.domain.commons.exceptions.engine.BaseBusinessException;
import com.mycompany.transport.common.errors.DefaultErrorResponse;
import com.mycompany.transport.common.errors.http.ErrorCodeToHttpStatusResolver;
import com.mycompany.transport.common.i18n.BusinessErrorMessageResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String TRACE_ID_KEY = "X-Flow-Id";
    private static final String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";

    private static final String FIELD_PAYLOAD_SEPARATOR = "|";
    private static final String FIELD_PAYLOAD_SPLIT_REGEX = "\\|";

    private static final List<String> CONSTRAINT_PRIORITY =
            List.of("NotBlank", "NotNull", "Size", "Pattern");

    private final BusinessErrorMessageResolver businessErrorMessageResolver;
    private final ErrorCodeToHttpStatusResolver httpStatusResolver;

    public GlobalExceptionHandler(
            BusinessErrorMessageResolver businessErrorMessageResolver,
            ErrorCodeToHttpStatusResolver httpStatusResolver
    ) {
        this.businessErrorMessageResolver =
                Objects.requireNonNull(businessErrorMessageResolver, "businessErrorMessageResolver");
        this.httpStatusResolver =
                Objects.requireNonNull(httpStatusResolver, "httpStatusResolver");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = buildValidationMessage(ex);
        String traceId = MDC.get(TRACE_ID_KEY);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = request.getRequestURI();

        LOGGER.error("[VALIDATION_ERROR] path='{}' traceId='{}' -> {}",
                path, traceId, message);

        DefaultErrorResponse body = DefaultErrorResponse.of(
                VALIDATION_ERROR_CODE,
                null,
                message,
                status.value(),
                path,
                traceId
        );
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<DefaultErrorResponse> handleBaseBusinessException(
            BaseBusinessException ex,
            HttpServletRequest request
    ) {
        Locale locale = request.getLocale() != null
                ? request.getLocale()
                : Locale.getDefault();

        String traceId = MDC.get(TRACE_ID_KEY);
        String path = request.getRequestURI();

        HttpStatus status = httpStatusResolver.resolve(ex.getCode());
        String localized = businessErrorMessageResolver.resolve(
                ex.getBusinessError(),
                locale,
                ex.getArgs()
        );

        LOGGER.error(
                "[BUSINESS_ERROR] code='{}' business='{}' path='{}' traceId='{}' message='{}' args={}",
                ex.getCode(),
                ex.getBusinessCode(),
                path,
                traceId,
                localized,
                ex.getArgs(),
                ex
        );

        DefaultErrorResponse body = DefaultErrorResponse.of(
                ex.getCode().name(),
                ex.getBusinessCode(),
                localized,
                status.value(),
                path,
                traceId
        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultErrorResponse> handleUnexpectedException(
            Exception ex,
            HttpServletRequest request
    ) {
        String traceId = MDC.get(TRACE_ID_KEY);
        String path = request.getRequestURI();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        LOGGER.error(
                "[UNEXPECTED_ERROR] path='{}' traceId='{}' -> {}",
                path,
                traceId,
                ex.getMessage(),
                ex
        );

        DefaultErrorResponse body = DefaultErrorResponse.of(
                ex.getClass().getSimpleName(),
                null,
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
            String field = fe.getField();
            String code = fe.getCode();
            String msg = fe.getDefaultMessage();

            if (!perField.containsKey(field)) {
                perField.put(field, code + FIELD_PAYLOAD_SEPARATOR + msg);
                return;
            }

            String currentCode =
                    perField.get(field).split(FIELD_PAYLOAD_SPLIT_REGEX, 2)[0];

            int newP = CONSTRAINT_PRIORITY.indexOf(code);
            int oldP = CONSTRAINT_PRIORITY.indexOf(currentCode);

            boolean shouldReplace =
                    (oldP < 0 && newP >= 0) || (newP >= 0 && newP < oldP);

            if (shouldReplace) {
                perField.put(field, code + FIELD_PAYLOAD_SEPARATOR + msg);
            }
        });

        return perField.entrySet().stream()
                .map(e -> {
                    String[] parts =
                            e.getValue().split(FIELD_PAYLOAD_SPLIT_REGEX, 2);
                    String message = (parts.length == 2 ? parts[1] : "");
                    return e.getKey() + ": " + message;
                })
                .collect(Collectors.joining("; "));
    }
}
