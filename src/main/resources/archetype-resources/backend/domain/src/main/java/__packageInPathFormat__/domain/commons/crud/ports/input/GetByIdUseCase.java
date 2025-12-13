package ${package}.domain.commons.crud.ports.input;

import java.util.Optional;

public interface GetByIdUseCase<ID, RES> {
    Optional<RES> handle(ID id);
}
