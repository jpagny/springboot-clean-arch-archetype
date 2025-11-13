package ${package}.transport.common.contracts;

@FunctionalInterface
public interface EndpointHandler<I, O> {
    O handle(I request);
}