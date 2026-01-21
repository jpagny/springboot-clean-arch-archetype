package ${package}.transport.endpoints.example.create.output;

import ${package}.domain.core.example.messages.ExampleMessageKey;
import ${package}.domain.core.example.operations.results.CreateExampleResult;
import ${package}.transport.common.contracts.OutputPresenter;
import ${package}.transport.endpoints.example.create.resolver.ExampleMessageResolver;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateExampleResultToResponseConverter
        implements OutputPresenter<CreateExampleResult, CreateExampleResponse> {

    private final ExampleMessageResolver messageResolver;

    public CreateExampleResultToResponseConverter(ExampleMessageResolver messageResolver) {
        this.messageResolver = Objects.requireNonNull(messageResolver, "messageResolver");
    }

    @Override
    public CreateExampleResponse toResponse(CreateExampleResult result) {
        String message = messageResolver.resolve(
                ExampleMessageKey.EXAMPLE_CREATED,
                result.name()
        );

        return new CreateExampleResponse(String.valueOf(result.id()), message);
    }
}
