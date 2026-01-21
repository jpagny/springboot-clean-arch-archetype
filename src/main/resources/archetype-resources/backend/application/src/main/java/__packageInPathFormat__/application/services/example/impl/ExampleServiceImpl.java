package ${package}.application.services.example.impl;

import ${package}.application.services.example.ExampleService;
import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.domain.core.example.operations.results.CreateExampleResult;
import ${package}.domain.core.example.ports.input.CreateExampleUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExampleServiceImpl implements ExampleService {

    private final CreateExampleUseCase useCase;

    public ExampleServiceImpl(CreateExampleUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    @Transactional
    public CreateExampleResult create(CreateExampleCommand command) {
        return useCase.handle(command);
    }
}
