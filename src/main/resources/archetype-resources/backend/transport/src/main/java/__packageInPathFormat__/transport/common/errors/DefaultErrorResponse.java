package ${package}.transport.common.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DefaultErrorResponse(
        String code,
        String businessCode,
        String message,
        Integer status,
        String path,
        String traceId,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Instant timestamp
) {
    public static DefaultErrorResponse of(String code, String businessCode, String message) {
        return new DefaultErrorResponse(code, businessCode, message, null, null, null, Instant.now());
    }

    public static DefaultErrorResponse of(String code, String businessCode, String message, int status, String path, String traceId) {
        return new DefaultErrorResponse(code, message, status, path, traceId, Instant.now());
    }
}
