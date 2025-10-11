package ${package}.bootstrap.config.web;

import ${package}.bootstrap.web.filter.CorrelationIdFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;

@Configuration
public class CorrelationIdConfiguration {

    @Bean
    public CorrelationIdFilter correlationIdFilter() {
        return new CorrelationIdFilter();
    }

    @Bean
    public FilterRegistrationBean<CorrelationIdFilter> correlationIdFilterRegistration(CorrelationIdFilter filter) {
        var reg = new FilterRegistrationBean<CorrelationIdFilter>();
        reg.setFilter(filter);
        reg.setName("correlationIdFilter");
        reg.setOrder(-101);
        reg.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC));
        return reg;
    }
}
