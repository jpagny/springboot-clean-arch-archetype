package ${package}.transport.common.i18n.impl;

import ${package}.domain.commons.exceptions.engine.ErrorContext;
import ${package}.domain.commons.exceptions.engine.IBusinessError;
import ${package}.transport.common.i18n.BusinessErrorMessageResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class BusinessErrorMessageResolverImpl implements BusinessErrorMessageResolver {

    private static final String ERROR_KEY_PREFIX = "error";
    private static final String KEY_SEPARATOR   = ".";
    private static final String DEFAULT_CONTEXT = "global";
    private static final String FALLBACK_INTERNAL_ERROR_CODE = "internal_error";

    private final MessageSourceAccessor accessor;

    @SuppressWarnings("DataFlowIssue")
    @Override
    public String resolve(IBusinessError error, Locale locale, Object... args) {
        if (error == null || error.code() == null) {
            return FALLBACK_INTERNAL_ERROR_CODE;
        }

        final var codeLower = error.code().name().toLowerCase(Locale.ROOT);
        final var context   = resolveContext(error);

        final var ctxKey = String.join(KEY_SEPARATOR, ERROR_KEY_PREFIX, context, codeLower);
        final var ctxMsg = accessor.getMessage(ctxKey, args, null, locale);
        if (ctxMsg != null) return ctxMsg;

        final var globalKey = String.join(KEY_SEPARATOR, ERROR_KEY_PREFIX, codeLower);
        final var globalMsg = accessor.getMessage(globalKey, args, null, locale);
        if (globalMsg != null) return globalMsg;

        return error.code().name();
    }

    private String resolveContext(IBusinessError error) {
        var ann = error.getClass().getAnnotation(ErrorContext.class);
        return (ann != null && ann.value() != null && !ann.value().isBlank())
                ? ann.value().toLowerCase(Locale.ROOT)
                : DEFAULT_CONTEXT;
    }
}
