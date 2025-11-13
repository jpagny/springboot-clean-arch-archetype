package ${package}.transport.common.contracts;

public interface InputPresenter<S, C> { C toCommand(S source); }
