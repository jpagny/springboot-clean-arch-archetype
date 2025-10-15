package ${package}.application.services.example;

import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.domain.core.example.operations.results.CreateExampleResult;

public interface ExampleService {
    CreateExampleResult create(CreateExampleCommand command);
}
