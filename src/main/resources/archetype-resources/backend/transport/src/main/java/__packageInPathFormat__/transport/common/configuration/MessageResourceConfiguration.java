package ${package}.transport.common.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Configuration for internationalization (i18n) message resources.
 *
 * <p>
 * This configuration belongs to the transport layer and is responsible for
 * loading and exposing localized message bundles used by API and application
 * layers (e.g. error messages, validation messages).
 * </p>
 *
 * <p>
 * Message bundles are automatically discovered from the {@code classpath:i18n}
 * directory. All {@code .properties} files are scanned and grouped by base name,
 * regardless of locale suffixes.
 * </p>
 *
 * <p>
 * The configuration ensures:
 * <ul>
 *   <li>UTF-8 encoding</li>
 *   <li>Deterministic ordering of message bundles</li>
 *   <li>No fallback to system locale</li>
 *   <li>Strict usage of message formatting</li>
 * </ul>
 * </p>
 */
@Slf4j
@Configuration
public class MessageResourceConfiguration {

    /**
     * Defines the {@link MessageSource} bean used for message resolution.
     *
     * <p>
     * All message bundles located under {@code classpath:i18n/*.properties}
     * are automatically detected and registered as basenames.
     * </p>
     *
     * @return the configured {@link MessageSource}
     */
    @Bean
    public MessageSource messageSource() {
        var resolver = new PathMatchingResourcePatternResolver();
        SortedSet<String> basenames = new TreeSet<>();

        try {
            var resources = resolver.getResources("classpath*:i18n/*.properties");
            for (var res : resources) {
                var filename = Objects.requireNonNull(res.getFilename());
                var base = filename.replaceFirst(
                        "(_[a-zA-Z]{2}(_[A-Z]{2})?)?\\.properties$",
                        ""
                );
                basenames.add("classpath:i18n/" + base);
            }
        } catch (IOException e) {
            log.warn("[i18n] Failed to scan i18n files", e);
        }

        var src = new ReloadableResourceBundleMessageSource();
        src.setBasenames(basenames.toArray(String[]::new));
        src.setDefaultEncoding(StandardCharsets.UTF_8.name());
        src.setFallbackToSystemLocale(false);
        src.setUseCodeAsDefaultMessage(false);
        src.setAlwaysUseMessageFormat(true);

        if (log.isDebugEnabled()) {
            log.debug("[i18n] Loaded message bundles (ordered): {}", basenames);
        }

        return src;
    }

    /**
     * Defines a {@link MessageSourceAccessor} for convenient message access.
     *
     * <p>
     * This accessor is configured with a default locale ({@link Locale#ENGLISH})
     * and is typically injected into transport or application components.
     * </p>
     *
     * @param messageSource the configured message source
     * @return a message source accessor
     */
    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource, Locale.ENGLISH);
    }
}
