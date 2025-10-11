package ${package}.domain.core.example.usecases;

import ${package}.domain.core.example.messages.ExampleMessageKey;
import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.domain.core.example.operations.results.CreateExampleResult;
import ${package}.domain.core.example.ports.input.CreateExampleUseCase;

public class CreateExampleUseCaseImpl implements CreateExampleUseCase {

    @Override
    public CreateExampleResult handle(CreateExampleCommand command) {
        var id = 1L;
        return new CreateExampleResult(id, ExampleMessageKey.EXAMPLE_CREATED, command.name());
    }

}
