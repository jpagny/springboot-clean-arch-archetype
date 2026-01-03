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

/**
 * HTTP filter responsible for managing correlation identifiers.
 *
 * <p>
 * This filter belongs to the bootstrap layer and is executed once per
 * incoming HTTP request. Its responsibility is to ensure that every
 * request is associated with a unique correlation identifier.
 * </p>
 *
 * <p>
 * The correlation identifier is:
 * <ul>
 *   <li>Read from the incoming request header if present</li>
 *   <li>Generated if missing</li>
 *   <li>Propagated to the response header</li>
 *   <li>Stored in the logging MDC for traceability</li>
 * </ul>
 * </p>
 *
 * <p>
 * This enables end-to-end request tracing across logs, services,
 * and external systems.
 * </p>
 */
public class CorrelationIdFilter extends OncePerRequestFilter {

    /**
     * HTTP header name used to carry the correlation identifier.
     */
    public static final String HEADER = "X-Flow-Id";

    /**
     * MDC key for the correlation identifier.
     */
    public static final String MDC_FLOW_ID = "X-Flow-Id";

    /**
     * MDC key for a monotonically increasing log number.
     */
    public static final String MDC_LOG_NUMBER = "logNumber";

    /**
     * MDC key for the request path.
     */
    public static final String MDC_PATH = "path";

    /**
     * Global counter used to generate incremental log numbers.
     */
    private static final AtomicLong LOG_COUNTER = new AtomicLong();

    /**
     * Applies correlation and logging context to the current request.
     *
     * @param request the incoming HTTP request
     * @param response the outgoing HTTP response
     * @param filterChain the filter chain
     * @throws ServletException in case of servlet errors
     * @throws IOException in case of I/O errors
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        var incoming = request.getHeader(HEADER);
        var correlationId = (incoming != null && !incoming.isBlank())
                ? incoming
                : UUID.randomUUID().toString();

        var logNumber = Long.toString(LOG_COUNTER.incrementAndGet());

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
