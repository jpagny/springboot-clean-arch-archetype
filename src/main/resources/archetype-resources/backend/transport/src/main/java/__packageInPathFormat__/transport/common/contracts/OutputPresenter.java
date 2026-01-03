package ${package}.transport.common.contracts;

/**
 * Contract for mapping application results to transport-level responses.
 *
 * <p>
 * This interface defines a presenter responsible for converting
 * application or domain results into transport-specific response
 * objects (DTOs, API responses, messages).
 * </p>
 *
 * <p>
 * It belongs to the transport layer and ensures that core application
 * models are not exposed directly to the outside world.
 * </p>
 *
 * @param <R> the result type produced by the application or domain layer
 * @param <D> the transport-level response type
 */
public interface OutputPresenter<R, D> {

    /**
     * Converts an application or domain result into a transport response.
     *
     * @param result the result produced by the application or domain layer
     * @return the corresponding transport response
     */
    D toResponse(R result);
}
