package ${package}.transport.endpoints.example.create.output;

{package}.transport.common.qualifier.PresentationConverter;
import ${package}.transport.endpoints.example.create.output.responses.CreateExampleResponse;
{package}.domain.core.example.messages.ExampleMessageKey;
{package}.transport.endpoints.example.create.resolver.ExampleMessageResolver;
{package}.domain.core.example.operations.results.CreateExampleResult;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
@PresentationConverter
@RequiredArgsConstructor
public class CreateExampleResultToResponseConverter
        implements Converter<CreateExampleResult, CreateExampleResponse> {

    private final ExampleMessageResolver messageResolver;

    @Override
    public CreateExampleResponse convert(MappingContext<CreateExampleResult, CreateExampleResponse> ctx) {
        var src = ctx.getSource();

        var message = messageResolver.resolve(
                ExampleMessageKey.EXAMPLE_CREATED,
                src.name()
        );

        return new CreateExampleResponse(String.valueOf(src.id()), message);
    }
}
