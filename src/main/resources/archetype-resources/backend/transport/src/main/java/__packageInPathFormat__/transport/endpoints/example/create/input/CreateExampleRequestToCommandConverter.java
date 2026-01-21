package ${package}.transport.endpoints.example.create.input;

import ${package}.domain.core.example.operations.commands.CreateExampleCommand;
import ${package}.transport.common.contracts.InputPresenter;
import org.springframework.stereotype.Component;

@Component
public class CreateExampleRequestToCommandConverter
        implements InputPresenter<CreateExampleRequest, CreateExampleCommand> {

    @Override
    public CreateExampleCommand toCommand(CreateExampleRequest request) {
        return new CreateExampleCommand(request.name());
    }
}
