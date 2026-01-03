package ${package}.bootstrap.configuration;

import ${package}.domain.core.example.ports.input.CreateExampleUseCase;
import ${package}.domain.core.example.usecases.CreateExampleUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bootstrap configuration for application use cases.
 *
 * <p>
 * This configuration class is responsible for wiring domain use case
 * implementations to their corresponding input ports.
 * </p>
 *
 * <p>
 * It belongs to the bootstrap layer and performs dependency wiring only.
 * No business logic must be defined in this class.
 * </p>
 */
@Configuration
public class UseCaseConfiguration {

    /**
     * Creates the {@link CreateExampleUseCase} bean.
     *
     * <p>
     * The returned instance is the concrete implementation of the
     * corresponding domain input port.
     * </p>
     *
     * @return the create example use case
     */
    @Bean
    CreateExampleUseCase createExampleUseCase() {
        return new CreateExampleUseCaseImpl();
    }
}
