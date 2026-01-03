package ${package}.transport.common.contracts;

/**
 * Contract for mapping transport-level input objects to application commands.
 *
 * <p>
 * This interface defines a presenter responsible for converting
 * incoming transport data (DTOs, requests, messages) into
 * command objects understood by the application or domain layer.
 * </p>
 *
 * <p>
 * It belongs to the transport layer and acts as a boundary that
 * prevents transport-specific representations from leaking
 * into the core of the application.
 * </p>
 *
 * @param <S> the source transport input type
 * @param <C> the target command type
 */
public interface InputPresenter<S, C> {

    /**
     * Converts a transport-level source object into a command.
     *
     * @param source the transport input
     * @return the corresponding command object
     */
    C toCommand(S source);
}
