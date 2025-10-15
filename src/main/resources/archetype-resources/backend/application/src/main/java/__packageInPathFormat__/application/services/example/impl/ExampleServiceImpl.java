package ${package}.application.services.example.impl;

import ${package}.application.services.example.ExampleService;
import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.domain.core.example.operations.results.CreateExampleResult;
import ${package}.domain.core.example.ports.input.CreateExampleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService {

    private final CreateExampleUseCase useCase;

    @Override
    @Transactional
    public CreateExampleResult create(CreateExampleCommand command) {
        return useCase.handle(command);
    }
}
