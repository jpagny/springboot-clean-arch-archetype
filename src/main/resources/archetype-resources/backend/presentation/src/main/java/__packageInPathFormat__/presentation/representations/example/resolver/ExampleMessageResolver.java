package ${package}.presentation.representations.example.resolver;

import ${package}.presentation.common.i18n.impl.MessageResolverImpl;
import ${package}.domain.core.example.messages.ExampleMessageKey;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ExampleMessageResolver extends MessageResolverImpl<ExampleMessageKey> {

    public ExampleMessageResolver(MessageSource messageSource) {
        super(messageSource, "example", "example.unknown");
    }

}
