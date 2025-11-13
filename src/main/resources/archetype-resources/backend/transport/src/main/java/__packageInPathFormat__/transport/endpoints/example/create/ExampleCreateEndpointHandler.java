package ${package}.transport.endpoints.example.create;

import ${package}.transport.common.contracts.EndpointHandler;
import ${package}.transport.common.contracts.InputPresenter;
import ${package}.transport.common.contracts.OutputPresenter;
import ${package}.application.services.example.ExampleService;
import ${package}.transport.endpoints.example.create.input.requests.CreateExampleRequest;
import ${package}.transport.endpoints.example.create.output.responses.CreateExampleResponse;
import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.domain.core.example.operations.results.CreateExampleResult;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExampleCreateEndpointHandler implements EndpointHandler<CreateExampleRequest, CreateExampleResponse> {

    private final InputPresenter<CreateExampleRequest, CreateExampleCommand> inPresenter;
    private final OutputPresenter<CreateExampleResult, CreateExampleResponse> outPresenter;
    private final ExampleService app;

    public CreateExampleResponse handle(CreateExampleRequest req) {
        var cmd = inPresenter.toCommand(req);
        var result = app.create(cmd);
        return outPresenter.toResponse(result);
    }
}
