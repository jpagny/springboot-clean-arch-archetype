package ${package}.transport.common.i18n;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Generic contract for resolving localized messages.
 *
 * <p>
 * This interface defines a generic mechanism for resolving messages
 * from a given key and locale, typically backed by an i18n message source.
 * </p>
 *
 * <p>
 * It belongs to the transport layer and abstracts away the underlying
 * internationalization mechanism (e.g. Spring {@code MessageSource}).
 * </p>
 *
 * @param <K> the message key type
 */
public interface MessageResolver<K> {

    /**
     * Resolves a localized message for the given key and locale.
     *
     * @param key the message key
     * @param locale the target locale
     * @param args optional arguments used for message formatting
     * @return the resolved localized message
     */
    String resolve(K key, Locale locale, Object... args);

    /**
     * Resolves a localized message using the current locale.
     *
     * <p>
     * The locale is obtained from {@link LocaleContextHolder}.
     * </p>
     *
     * @param key the message key
     * @param args optional arguments used for message formatting
     * @return the resolved localized message
     */
    default String resolve(K key, Object... args) {
        var locale = LocaleContextHolder.getLocale();
        return resolve(key, locale, args);
    }
}
