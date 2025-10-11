package ${package}.entrypoint.rest.endpoints.example.message;

import ${package}.entrypoint.rest.common.resolver.message.impl.MessageResolverImpl;
import ${package}.domain.core.example.messages.ExampleMessageKey;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ExampleMessageLocalizer extends MessageResolverImpl<ExampleMessageKey> {
    public ExampleMessageLocalizer(MessageSource messageSource) {
        super(messageSource, "example","unknown");
    }
}