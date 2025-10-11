package ${package}.entrypoint.rest.endpoints.example.controller;

import ${package}.presentation.representations.example.facade.ExampleEndpointFacade;
import ${package}.presentation.representations.example.input.requests.CreateExampleRequest;
import ${package}.presentation.representations.example.output.responses.CreateExampleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/examples")
public class ExampleController {

    private final ExampleEndpointFacade facade;

    @PostMapping
    public ResponseEntity<CreateExampleResponse> create(@RequestBody CreateExampleRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.create(req));
    }

}

