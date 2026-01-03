package ${package}.bootstrap.configuration;

import ${package}.bootstrap.web.filter.CorrelationIdFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;

/**
 * Bootstrap configuration for the correlation ID HTTP filter.
 *
 * <p>
 * This configuration class is responsible for instantiating and registering
 * the {@link CorrelationIdFilter} in the servlet filter chain.
 * </p>
 *
 * <p>
 * It belongs to the bootstrap layer and wires cross-cutting infrastructure
 * concerns such as request correlation and logging context initialization.
 * </p>
 *
 * <p>
 * The filter is explicitly registered to ensure:
 * <ul>
 *   <li>Early execution in the filter chain</li>
 *   <li>Support for both REQUEST and ASYNC dispatcher types</li>
 *   <li>Deterministic ordering across the application</li>
 * </ul>
 * </p>
 */
@Configuration
public class CorrelationIdConfiguration {

    /**
     * Creates the correlation ID filter.
     *
     * @return a new {@link CorrelationIdFilter}
     */
    @Bean
    public CorrelationIdFilter correlationIdFilter() {
        return new CorrelationIdFilter();
    }

    /**
     * Registers the correlation ID filter with the servlet container.
     *
     * @param filter the correlation ID filter
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<CorrelationIdFilter> correlationIdFilterRegistration(
            CorrelationIdFilter filter
    ) {
        var reg = new FilterRegistrationBean<CorrelationIdFilter>();
        reg.setFilter(filter);
        reg.setName("correlationIdFilter");
        reg.setOrder(-101);
        reg.setDispatcherTypes(
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC)
        );
        return reg;
    }
}
