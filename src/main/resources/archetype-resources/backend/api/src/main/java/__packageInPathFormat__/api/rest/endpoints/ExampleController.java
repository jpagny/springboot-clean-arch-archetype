package ${package}.api.rest.endpoints;

import ${package}.transport.endpoints.example.create.ExampleCreateEndpointHandler;
import ${package}.transport.endpoints.example.create.input.CreateExampleRequest;
import ${package}.transport.endpoints.example.create.output.CreateExampleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/examples")
public class ExampleController {

    private final ExampleCreateEndpointHandler createEndpointHandler;

    @PostMapping()
    public ResponseEntity<CreateExampleResponse> create(@Valid @RequestBody CreateExampleRequest req) {
        var response = createEndpointHandler.handle(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

