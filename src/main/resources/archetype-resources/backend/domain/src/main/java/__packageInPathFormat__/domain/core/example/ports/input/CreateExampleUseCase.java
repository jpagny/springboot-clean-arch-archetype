package ${package}.domain.core.example.ports.input;

import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.domain.core.example.operations.results.CreateExampleResult;

public interface CreateExampleUseCase {
    CreateExampleResult handle(CreateExampleCommand command);
}
