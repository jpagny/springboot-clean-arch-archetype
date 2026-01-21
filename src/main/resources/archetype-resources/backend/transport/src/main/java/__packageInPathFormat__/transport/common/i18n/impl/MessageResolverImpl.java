package ${package}.transport.common.i18n.impl;

import ${package}.transport.common.i18n.MessageResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;

public abstract class MessageResolverImpl<K extends Enum<K>> implements MessageResolver<K> {

    private static final Logger log =
            LoggerFactory.getLogger(MessageResolverImpl.class);

    private static final String SEPARATOR = ".";

    private final MessageSourceAccessor accessor;
    private final String prefix;
    private final String defaultCode;

    protected MessageResolverImpl(
            MessageSource messageSource,
            String prefix,
            String defaultCode) {
        this.accessor = new MessageSourceAccessor(messageSource);
        this.prefix = prefix;
        this.defaultCode = defaultCode;
    }

    @Override
    public String resolve(K key, Locale locale, Object... args) {
        String code = (key == null)
                ? defaultCode
                : prefix + SEPARATOR + key.name().toLowerCase(Locale.ROOT);

        try {
            return accessor.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            log.warn(
                    "[i18n] Missing translation for key '{}' (locale={}) — using fallback '{}'",
                    code,
                    locale,
                    defaultCode
            );
            return accessor.getMessage(defaultCode, args, defaultCode, locale);
        }
    }

    @Override
    public String resolve(K key, Object... args) {
        return resolve(key, LocaleContextHolder.getLocale(), args);
    }
}
