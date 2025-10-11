package ${package}.entrypoint.rest.endpoints.example.error;

import ${package}.entrypoint.rest.common.mapping.error.ErrorMapping;
import ${package}.domain.commons.exceptions.engine.IBusinessError;
import ${package}.domain.core.example.exceptions.ExampleBusinessError;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExampleErrorMapping implements ErrorMapping {
    @Override
    public Map<IBusinessError, String> mappings() {
        return Map.of(
                ExampleBusinessError.EXAMPLE_ERROR, "example.error.example-error"
        );
    }
}
