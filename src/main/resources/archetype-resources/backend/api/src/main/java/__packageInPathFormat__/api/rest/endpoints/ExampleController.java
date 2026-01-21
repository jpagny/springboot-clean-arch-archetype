package ${package}.api.rest.endpoints;

import ${package}.transport.endpoints.example.create.ExampleCreateEndpointHandler;
import ${package}.transport.endpoints.example.create.input.CreateExampleRequest;
import ${package}.transport.endpoints.example.create.output.CreateExampleResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/examples")
public class ExampleController {

    private final ExampleCreateEndpointHandler createEndpointHandler;

    public ExampleController(ExampleCreateEndpointHandler createEndpointHandler) {
        this.createEndpointHandler = createEndpointHandler;
    }

    @PostMapping
    public ResponseEntity<CreateExampleResponse> create(
            @Valid @RequestBody CreateExampleRequest req) {
        CreateExampleResponse response = createEndpointHandler.handle(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
