public abstract class AbstractDeleteUseCase<M, ID>
        implements DeleteUseCase<ID> {

    private final RepositoryPort<M, ID> repository;

    protected AbstractDeleteUseCase(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public void handle(ID id) {
        beforeDelete(id);
        repository.deleteById(id);
        afterDelete(id);
    }

    protected void beforeDelete(ID id) {}
    protected void afterDelete(ID id) {}
}
