package ${package}.transport.endpoints.example.create.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateExampleRequest(
        @NotBlank(message = "{error.missing_required_field}")
        @Size(min = 3, message = "{error.invalid_value}")
        String name
) {

}
