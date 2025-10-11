package ${package}.presentation.common.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Configuration
public class MessageResourceConfiguration {

    @Bean
    public MessageSource messageSource() {
        var resolver = new PathMatchingResourcePatternResolver();
        Set<String> basenames = new HashSet<>();

        try {
            var resources = resolver.getResources("classpath*:i18n/*.properties");
            for (var res : resources) {
                String filename = Objects.requireNonNull(res.getFilename());
                String base = filename.replaceFirst("(_[a-zA-Z]{2}(_[A-Z]{2})?)?\\.properties$", "");
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

        if (log.isDebugEnabled()) {
            log.debug("[i18n] Loaded message bundles: {}", basenames);
        }

        return src;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }
}
