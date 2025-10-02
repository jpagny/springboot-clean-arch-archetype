package ${package}.domain.commons.exceptions;

public interface IBaseException {
    ErrorCode getCode();
    String getMessage();
    Object[] getArgs();
}
