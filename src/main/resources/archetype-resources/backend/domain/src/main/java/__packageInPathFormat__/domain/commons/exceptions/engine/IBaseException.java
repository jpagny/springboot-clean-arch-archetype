package ${package}.domain.commons.exceptions.engine;

import ${package}.domain.commons.exceptions.codes.ErrorCode;

public interface IBaseException {
    ErrorCode getCode();
    IBusinessError getBusinessError();
    Object[] getArgs();
}
