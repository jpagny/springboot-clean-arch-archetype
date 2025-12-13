package ${package}.external.common.persistence.mapping;

public interface PersistenceMapper<M, E> {

    E toEntity(M model);

    M toModel(E entity);
}
