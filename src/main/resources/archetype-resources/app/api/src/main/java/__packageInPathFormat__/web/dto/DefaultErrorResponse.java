package ${package}.web.dto;

public record DefaultErrorResponse(String code, String message) {

    public static DefaultErrorResponse of(String code, String message) {
        return new DefaultErrorResponse(code, message);
    }

}
