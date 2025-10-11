package ${package}.api.shared.resolver.message.impl;

import ${package}.api.shared.resolver.message.MessageResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;

public abstract class MessageResolverImpl<K extends Enum<K>> implements MessageResolver<K> {

    private final MessageSourceAccessor accessor;
    private final String prefix;
    private final String defaultCode;
    private static final String SEPARATOR = ".";

    public MessageResolverImpl(MessageSource messageSource, String prefix, String defaultCode) {
        this.accessor = new MessageSourceAccessor(messageSource);
        this.prefix = prefix;
        this.defaultCode = defaultCode;
    }

    @Override
    public String resolve(K key, Locale locale, Object... args) {
        String code = (key == null)
                ? defaultCode
                : prefix + SEPARATOR + key.name().toLowerCase(Locale.ROOT);
        return accessor.getMessage(code, args, code, locale);
    }
}
