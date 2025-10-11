package ${package}.entrypoint.rest.common.resolver.error.message;

import ${package}.domain.commons.exceptions.engine.IBusinessError;

import java.util.Locale;

public interface BusinessErrorMessageResolver {
    String resolve(IBusinessError error, Locale locale, Object... args);
}
