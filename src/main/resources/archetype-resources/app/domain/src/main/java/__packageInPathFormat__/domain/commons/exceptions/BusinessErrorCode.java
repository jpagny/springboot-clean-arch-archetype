package ${package}.domain.commons.exceptions;

public enum BusinessErrorCode {

    A_BUSINESS_ERROR(ErrorCode.AN_ERROR_CODE, "Resource with id {0} was not found");

    private final ErrorCode code;
    private final String messageTemplate;

    BusinessErrorCode(ErrorCode code, String messageTemplate) {
        this.code = code;
        this.messageTemplate = messageTemplate;
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }
}
