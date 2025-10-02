package ${package}.bootstrap.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CorrelationIdFilter extends OncePerRequestFilter {

    public static final String HEADER = "X-Flow-Id";
    public static final String MDC_FLOW_ID = "X-Flow-Id";
    public static final String MDC_LOG_NUMBER = "logNumber";
    public static final String MDC_PATH = "path";

    private static final AtomicLong LOG_COUNTER = new AtomicLong();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String incoming = request.getHeader(HEADER);
        String correlationId = (incoming != null && !incoming.isBlank())
                ? incoming
                : UUID.randomUUID().toString();

        String logNumber = Long.toString(LOG_COUNTER.incrementAndGet());

        MDC.put(MDC_FLOW_ID, correlationId);
        MDC.put(MDC_LOG_NUMBER, logNumber);
        MDC.put(MDC_PATH, request.getRequestURI());

        response.setHeader(HEADER, correlationId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_FLOW_ID);
            MDC.remove(MDC_LOG_NUMBER);
            MDC.remove(MDC_PATH);
        }
    }
}
