package ${package}.transport.common.contracts;

/**
 * Functional contract for handling transport-level requests.
 *
 * <p>
 * This interface represents a generic handler used at the transport layer
 * to process incoming requests and produce responses.
 * </p>
 *
 * <p>
 * It acts as an intermediary abstraction between API adapters
 * (REST, GraphQL, messaging, etc.) and application or domain use cases.
 * </p>
 *
 * <p>
 * Being a {@link FunctionalInterface}, it can be implemented using
 * lambda expressions or method references.
 * </p>
 *
 * @param <I> the input request type
 * @param <O> the output response type
 */
@FunctionalInterface
public interface EndpointHandler<I, O> {

    /**
     * Handles the given transport request.
     *
     * @param request the incoming request
     * @return the response produced by the handler
     */
    O handle(I request);
}
