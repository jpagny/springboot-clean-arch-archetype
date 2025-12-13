package ${package}.domain.commons.crud.ports.input;

public interface DeleteUseCase<ID> {
    void handle(ID id);
}
