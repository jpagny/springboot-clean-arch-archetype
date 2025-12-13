package ${package}.external.common.persistence.mapping;

public interface IdMapper<ID, DBID> {

    DBID toDbId(ID id);

    default ID toDomainId(DBID dbId) {
        throw new UnsupportedOperationException("toDomainId not implemented");
    }
}
