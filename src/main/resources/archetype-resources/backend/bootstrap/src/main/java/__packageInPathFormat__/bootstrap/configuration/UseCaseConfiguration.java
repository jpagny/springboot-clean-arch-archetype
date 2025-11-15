package ${package}.bootstrap.configuration;

import ${package}.domain.core.example.ports.input.CreateExampleUseCase;
import ${package}.domain.core.example.usecases.CreateExampleUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    CreateExampleUseCase createExampleUseCase() {
        return new CreateExampleUseCaseImpl();
    }

}