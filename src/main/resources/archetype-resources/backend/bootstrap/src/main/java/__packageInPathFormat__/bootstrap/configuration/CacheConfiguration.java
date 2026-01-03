package ${package}.bootstrap.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Cache configuration bootstrap.
 *
 * <p>
 * This configuration class enables Spring’s caching abstraction for the
 * application. It belongs to the bootstrap layer and is responsible for
 * activating cross-cutting caching concerns.
 * </p>
 *
 * <p>
 * The actual cache implementation (e.g. Caffeine, Redis, Ehcache)
 * is configured separately via Spring beans or external configuration.
 * </p>
 *
 * <p>
 * This class does not contain business logic and only activates
 * infrastructure-level capabilities.
 * </p>
 */
@Configuration
@EnableCaching
public class CacheConfiguration {
}
