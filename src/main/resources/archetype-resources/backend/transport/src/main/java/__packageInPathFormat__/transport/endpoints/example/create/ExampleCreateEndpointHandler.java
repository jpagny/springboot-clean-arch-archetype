package ${package}.transport.endpoints.example.create;

import ${package}.application.services.example.ExampleService;
import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.domain.core.example.operations.results.CreateExampleResult;
import ${package}.transport.common.contracts.EndpointHandler;
import ${package}.transport.common.contracts.OutputPresenter;
import ${package}.transport.endpoints.example.create.input.CreateExampleRequest;
import ${package}.transport.endpoints.example.create.output.CreateExampleResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExampleCreateEndpointHandler
        implements EndpointHandler<CreateExampleRequest, CreateExampleResponse> {

    private final OutputPresenter<CreateExampleResult, CreateExampleResponse> outPresenter;
    private final ExampleService app;

    public ExampleCreateEndpointHandler(
            OutputPresenter<CreateExampleResult, CreateExampleResponse> outPresenter,
            ExampleService app
    ) {
        this.outPresenter = Objects.requireNonNull(outPresenter, "outPresenter");
        this.app = Objects.requireNonNull(app, "app");
    }

    @Override
    public CreateExampleResponse handle(CreateExampleRequest req) {
        var cmd = new CreateExampleCommand(req.name());
        var result = app.create(cmd);
        return outPresenter.toResponse(result);
    }
}
