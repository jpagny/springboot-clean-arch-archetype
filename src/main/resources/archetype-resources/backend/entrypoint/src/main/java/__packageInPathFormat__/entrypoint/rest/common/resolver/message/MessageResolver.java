package ${package}.entrypoint.rest.common.resolver.message;

import java.util.Locale;

public interface MessageResolver<K> {
    String resolve(K key, Locale locale, Object... args);
}
