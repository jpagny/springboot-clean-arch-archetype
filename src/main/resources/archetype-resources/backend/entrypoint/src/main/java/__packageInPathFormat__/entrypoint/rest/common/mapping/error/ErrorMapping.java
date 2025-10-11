package ${package}.entrypoint.rest.common.mapping.error;

import ${package}.domain.commons.exceptions.engine.IBusinessError;

import java.util.Map;

public interface ErrorMapping {
    Map<? extends IBusinessError, String> mappings();
}
