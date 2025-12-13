package ${package}.domain.commons.crud.ports.input;

public interface UpdateUseCase<ID, CMD, RES> {
    RES handle(ID id, CMD command);
}
