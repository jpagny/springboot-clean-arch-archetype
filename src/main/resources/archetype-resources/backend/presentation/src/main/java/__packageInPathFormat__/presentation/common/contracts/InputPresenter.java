package ${package}.presentation.common.contracts;

public interface InputPresenter<S, C> { C toCommand(S source); }
