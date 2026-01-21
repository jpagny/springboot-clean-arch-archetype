package ${package}.transport.endpoints.example.create;

import ${package}.transport.common.contracts.EndpointHandler;
import ${package}.transport.common.contracts.InputPresenter;
import ${package}.transport.common.contracts.OutputPresenter;
import ${package}.application.services.example.ExampleService;
import ${package}.transport.endpoints.example.create.input.CreateExampleRequest;
import ${package}.transport.endpoints.example.create.output.CreateExampleResponse;
import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.domain.core.example.operations.results.CreateExampleResult;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExampleCreateEndpointHandler
        implements EndpointHandler<CreateExampleRequest, CreateExampleResponse> {

    private final InputPresenter<CreateExampleRequest, CreateExampleCommand> inPresenter;
    private final OutputPresenter<CreateExampleResult, CreateExampleResponse> outPresenter;
    private final ExampleService app;

    public ExampleCreateEndpointHandler(
            InputPresenter<CreateExampleRequest, CreateExampleCommand> inPresenter,
            OutputPresenter<CreateExampleResult, CreateExampleResponse> outPresenter,
            ExampleService app
    ) {
        this.inPresenter = Objects.requireNonNull(inPresenter, "inPresenter");
        this.outPresenter = Objects.requireNonNull(outPresenter, "outPresenter");
        this.app = Objects.requireNonNull(app, "app");
    }

    @Override
    public CreateExampleResponse handle(CreateExampleRequest req) {
        var cmd = inPresenter.toCommand(req);
        var result = app.create(cmd);
        return outPresenter.toResponse(result);
    }
}
