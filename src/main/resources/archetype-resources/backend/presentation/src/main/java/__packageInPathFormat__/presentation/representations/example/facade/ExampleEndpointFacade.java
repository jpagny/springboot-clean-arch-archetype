package ${package}.presentation.representations.example.facade;

import ${package}.presentation.common.contracts.InputPresenter;
import ${package}.presentation.common.contracts.OutputPresenter;
import ${package}.application.services.example.ExampleService;
import ${package}.presentation.representations.example.input.requests.CreateExampleRequest;
import ${package}.presentation.representations.example.output.responses.CreateExampleResponse;
import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.domain.core.example.operations.results.CreateExampleResult;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExampleEndpointFacade {

    private final InputPresenter<CreateExampleRequest, CreateExampleCommand> inPresenter;
    private final OutputPresenter<CreateExampleResult, CreateExampleResponse> outPresenter;
    private final ExampleService app;

    public CreateExampleResponse create(CreateExampleRequest req) {
        var cmd = inPresenter.toCommand(req);
        var result = app.create(cmd);
        return outPresenter.toResponse(result);
    }
}
