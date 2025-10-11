package ${package}.entrypoint.rest.common.advice;

import ${package}.entrypoint.rest.common.dto.DefaultErrorResponse;
import ${package}.entrypoint.rest.common.resolver.error.message.impl.BusinessErrorMessageResolverImpl;
import ${package}.entrypoint.rest.common.resolver.error.http.ErrorCodeToHttpStatusResolver;
import ${package}.domain.commons.exceptions.engine.BaseBusinessException;
import ${package}.domain.commons.exceptions.codes.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERROR_PREFIX = "error.";
    public static final String TRACE_ID_KEY = "X-Flow-Id";

    private final BusinessErrorMessageResolverImpl businessErrorMessageResolver;
    private final ErrorCodeToHttpStatusResolver httpStatusResolver;
    private final MessageSource messageSource;

    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<DefaultErrorResponse> handleBaseBusinessException(BaseBusinessException ex,
                                                                            HttpServletRequest request) {
        var locale = LocaleContextHolder.getLocale();
        var traceId = MDC.get(TRACE_ID_KEY);
        var path = request.getRequestURI();

        var status = httpStatusResolver.resolve(ex.getCode());
        var  localized = businessErrorMessageResolver.resolve(ex.getBusinessError(), locale, ex.getArgs());

        var body = DefaultErrorResponse.of(ex.getCode().name(), localized, status.value(), path, traceId);

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {

        log.error("Unhandled exception", ex);

        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var traceId = MDC.get(TRACE_ID_KEY);
        var path = request.getRequestURI();
        var body = DefaultErrorResponse.of(ex.getClass().getSimpleName(), ex.getMessage(),
                status.value(), path, traceId);

        return ResponseEntity.status(status).body(body);
    }

    private String resolveMessage(ErrorCode code, Object[] args, String defaultMsg) {
        var locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(
                ERROR_PREFIX + code.name().toLowerCase(Locale.ROOT),
                args,
                defaultMsg,
                locale
        );
    }
}