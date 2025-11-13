package ${package}.transport.endpoints.example.create.input.presenters;

import ${package}.transport.common.contracts.InputPresenter;
import ${package}.transport.endpoints.example.create.input.requests.CreateExampleRequest;
import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CreateExampleInputPresenter
        implements InputPresenter<CreateExampleRequest, CreateExampleCommand> {

    private final ModelMapper mapper;

    public CreateExampleInputPresenter(
            @Qualifier("presentationMapper") ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CreateExampleCommand toCommand(CreateExampleRequest src) {
        return mapper.map(src, CreateExampleCommand.class);
    }
}
