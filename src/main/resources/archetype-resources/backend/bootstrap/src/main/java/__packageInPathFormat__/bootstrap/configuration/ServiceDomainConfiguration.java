package ${package}.bootstrap.configuration;

import org.springframework.context.annotation.Configuration;

/**
 * Bootstrap configuration for domain services.
 *
 * <p>
 * This configuration class is responsible for wiring domain service
 * implementations (domain-level components) and exposing them as beans
 * when they are required by use cases or adapters.
 * </p>
 *
 * <p>
 * It belongs to the bootstrap layer and must contain wiring only
 * (no business logic). The domain layer remains framework-agnostic;
 * Spring beans are created here to connect the application graph.
 * </p>
 */
@Configuration
public class ServiceDomainConfiguration {

}
