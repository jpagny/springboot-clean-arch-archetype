package ${package}.transport.endpoints.example.create.output.presenters;

import ${package}.transport.common.contracts.OutputPresenter;
import ${package}.domain.core.example.operations.results.CreateExampleResult;
import ${package}.transport.endpoints.example.create.output.responses.CreateExampleResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CreateExampleOutputPresenter
        implements OutputPresenter<CreateExampleResult, CreateExampleResponse> {

    private final ModelMapper mapper;

    public CreateExampleOutputPresenter(
            @Qualifier("presentationMapper") ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CreateExampleResponse toResponse(CreateExampleResult result) {
        return mapper.map(result, CreateExampleResponse.class);
    }
}
