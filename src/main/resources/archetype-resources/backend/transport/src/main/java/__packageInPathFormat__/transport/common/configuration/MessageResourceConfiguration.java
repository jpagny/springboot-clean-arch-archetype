package ${package}.transport.common.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 */
@Configuration
public class MessageResourceConfiguration {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(MessageResourceConfiguration.class);

    @Bean
    public MessageSource messageSource() {
        PathMatchingResourcePatternResolver resolver =
                new PathMatchingResourcePatternResolver();
        SortedSet<String> basenames = new TreeSet<>();

        try {
            var resources = resolver.getResources("classpath*:i18n/*.properties");
            for (var res : resources) {
                String filename = Objects.requireNonNull(res.getFilename());
                String base = filename.replaceFirst(
                        "(_[a-zA-Z]{2}(_[A-Z]{2})?)?\\.properties$",
                        ""
                );
                basenames.add("classpath:i18n/" + base);
            }
        } catch (IOException e) {
            LOGGER.warn("[i18n] Failed to scan i18n files", e);
        }

        ReloadableResourceBundleMessageSource src =
                new ReloadableResourceBundleMessageSource();
        src.setBasenames(basenames.toArray(String[]::new));
        src.setDefaultEncoding(StandardCharsets.UTF_8.name());
        src.setFallbackToSystemLocale(false);
        src.setUseCodeAsDefaultMessage(false);
        src.setAlwaysUseMessageFormat(true);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[i18n] Loaded message bundles (ordered): {}", basenames);
        }

        return src;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource, Locale.ENGLISH);
    }
}
