package ${package}.entrypoint.rest.common.mapping.error;

import ${package}.domain.commons.exceptions.engine.IBusinessError;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BusinessErrorMessageMappings {

    private final Map<IBusinessError, String> merged;

    public BusinessErrorMessageMappings(List<ErrorMapping> contributors) {
        var acc = new HashMap<IBusinessError, String>();

        for (ErrorMapping c : contributors) {
            Map<? extends IBusinessError, String> map = Objects.requireNonNullElse(c.mappings(), Map.of());
            map.forEach(acc::putIfAbsent);
        }

        this.merged = Collections.unmodifiableMap(acc);
    }

    public Map<IBusinessError, String> all() {
        return merged;
    }
}
