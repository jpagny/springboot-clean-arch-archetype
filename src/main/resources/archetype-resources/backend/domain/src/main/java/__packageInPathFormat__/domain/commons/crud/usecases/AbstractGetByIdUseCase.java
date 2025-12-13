public abstract class AbstractGetByIdUseCase<M, ID, RES>
        implements GetByIdUseCase<ID, RES> {

    private final RepositoryPort<M, ID> repository;

    protected AbstractGetByIdUseCase(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Optional<RES> handle(ID id) {
        return repository.findById(id).map(this::toResult);
    }

    protected abstract RES toResult(M model);
}
