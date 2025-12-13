package ${package}.domain.commons.crud.ports.input;

public interface CreateUseCase<CMD, RES> {
    RES handle(CMD command);
}
