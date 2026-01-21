package ${package}.transport.endpoints.example.create.output;

import ${package}.transport.common.qualifier.PresentationConverter;
import ${package}.domain.core.example.messages.ExampleMessageKey;
import ${package}.transport.endpoints.example.create.resolver.ExampleMessageResolver;
import ${package}.domain.core.example.operations.results.CreateExampleResult;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
@PresentationConverter
public class CreateExampleResultToResponseConverter
        implements Converter<CreateExampleResult, CreateExampleResponse> {

    private final ExampleMessageResolver messageResolver;

    public CreateExampleResultToResponseConverter(ExampleMessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @Override
    public CreateExampleResponse convert(
            MappingContext<CreateExampleResult, CreateExampleResponse> ctx) {

        CreateExampleResult src = ctx.getSource();

        String message = messageResolver.resolve(
                ExampleMessageKey.EXAMPLE_CREATED,
                src.name()
        );

        return new CreateExampleResponse(String.valueOf(src.id()), message);
    }
}
