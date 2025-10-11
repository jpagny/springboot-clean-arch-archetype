package ${package}.application.services.example;

import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.domain.core.example.operations.results.CreateExampleResult;
import ${package}.domain.core.example.ports.input.CreateExampleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ExampleService {

    private final CreateExampleUseCase useCase;

    @Transactional
    public CreateExampleResult create(CreateExampleCommand command) {
        return useCase.handle(command);
    }

}
