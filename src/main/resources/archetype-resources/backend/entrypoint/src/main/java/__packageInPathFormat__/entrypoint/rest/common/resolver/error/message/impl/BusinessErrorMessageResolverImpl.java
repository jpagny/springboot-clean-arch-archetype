package ${package}.entrypoint.rest.common.resolver.error.message.impl;

import ${package}.entrypoint.rest.common.resolver.error.message.BusinessErrorMessageResolver;
import ${package}.entrypoint.rest.common.mapping.error.BusinessErrorMessageMappings;
import ${package}.domain.commons.exceptions.engine.IBusinessError;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

@Component
public class BusinessErrorMessageResolverImpl implements BusinessErrorMessageResolver {

    private final Map<IBusinessError, String> keys;
    private final MessageSource messageSource;

    public BusinessErrorMessageResolverImpl(BusinessErrorMessageMappings mappings, MessageSource messageSource) {
        this.keys = mappings.all();
        this.messageSource = messageSource;
    }

    public String resolve(IBusinessError error, Locale locale, Object... args) {
        String key = keys.getOrDefault(error, "error.unknown");
        return messageSource.getMessage(key, args, key, locale);
    }
}
