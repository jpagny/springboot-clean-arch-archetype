package ${package}.transport.endpoints.example.create.input;

{package}.transport.common.qualifier.PresentationConverter;
{package}.transport.endpoints.example.create.input.requests.CreateExampleRequest;
import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
@PresentationConverter
public class CreateExampleRequestToCommandConverter
        implements Converter<CreateExampleRequest, CreateExampleCommand> {

    @Override
    public CreateExampleCommand convert(
            MappingContext<CreateExampleRequest, CreateExampleCommand> ctx) {

        var s = ctx.getSource();
        return new CreateExampleCommand(s.name());
    }
}
