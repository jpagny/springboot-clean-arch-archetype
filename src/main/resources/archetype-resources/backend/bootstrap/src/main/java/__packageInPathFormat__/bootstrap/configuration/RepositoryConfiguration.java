package ${package}.bootstrap.configuration;

import org.springframework.context.annotation.Configuration;

/**
 * Bootstrap configuration for repository adapters.
 *
 * <p>
 * This configuration class is responsible for wiring repository adapters
 * that implement domain repository ports.
 * </p>
 *
 * <p>
 * It belongs to the bootstrap layer and connects:
 * <ul>
 *   <li>Domain repository ports</li>
 *   <li>Infrastructure / external repository adapters (JPA, JDBC, etc.)</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class must only contain wiring logic (@Bean definitions) and must
 * not include any business or persistence logic.
 * </p>
 */
@Configuration
public class RepositoryConfiguration {

}
