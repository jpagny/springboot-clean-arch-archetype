package ${package}.presentation.representations.example.input.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateExampleRequest(
        @NotBlank(message = "{error.missing_required_field}")
        @Size(min = 3, message = "{error.invalid_value}")
        String name
) {

}
