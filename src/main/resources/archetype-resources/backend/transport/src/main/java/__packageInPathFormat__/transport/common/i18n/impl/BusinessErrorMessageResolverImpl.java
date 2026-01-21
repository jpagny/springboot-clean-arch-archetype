package ${package}.transport.common.i18n.impl;

import ${package}.domain.commons.exceptions.engine.ErrorContext;
import ${package}.domain.commons.exceptions.engine.IBusinessError;
import ${package}.transport.common.i18n.BusinessErrorMessageResolver;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BusinessErrorMessageResolverImpl implements BusinessErrorMessageResolver {

    private static final String ERROR_KEY_PREFIX = "error";
    private static final String KEY_SEPARATOR   = ".";
    private static final String DEFAULT_CONTEXT = "global";
    private static final String FALLBACK_INTERNAL_ERROR_CODE = "internal_error";

    private final MessageSourceAccessor accessor;

    public BusinessErrorMessageResolverImpl(MessageSourceAccessor accessor) {
        this.accessor = accessor;
    }

    @Override
    public String resolve(IBusinessError error, Locale locale, Object... args) {
        if (error == null || error.code() == null) {
            return FALLBACK_INTERNAL_ERROR_CODE;
        }

        String codeLower = error.code().name().toLowerCase(Locale.ROOT);
        String context   = resolveContext(error);

        String ctxKey = String.join(KEY_SEPARATOR, ERROR_KEY_PREFIX, context, codeLower);
        String ctxMsg = tryResolve(ctxKey, locale, args);
        if (ctxMsg != null) {
            return ctxMsg;
        }

        String globalKey = String.join(KEY_SEPARATOR, ERROR_KEY_PREFIX, codeLower);
        String globalMsg = tryResolve(globalKey, locale, args);
        if (globalMsg != null) {
            return globalMsg;
        }

        return error.code().name();
    }

    private String resolveContext(IBusinessError error) {
        ErrorContext ann = error.getClass().getAnnotation(ErrorContext.class);
        return (ann != null && ann.value() != null && !ann.value().isBlank())
                ? ann.value().toLowerCase(Locale.ROOT)
                : DEFAULT_CONTEXT;
    }

    private String tryResolve(String key, Locale locale, Object... args) {
        try {
            return accessor.getMessage(key, args, locale);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

}
