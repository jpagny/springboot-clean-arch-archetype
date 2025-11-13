package ${package}.transport.common.i18n;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public interface MessageResolver<K> {

    String resolve(K key, Locale locale, Object... args);

    default String resolve(K key, Object... args) {
        var locale = LocaleContextHolder.getLocale();
        return resolve(key, locale, args);
    }

}
