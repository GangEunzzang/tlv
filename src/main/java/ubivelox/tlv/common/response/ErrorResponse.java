package ubivelox.tlv.common.response;

import lombok.Getter;
import ubivelox.tlv.common.exception.ResultCode;

@Getter
public class ErrorResponse extends APIResponse {

    private ErrorResponse(ResultCode code) {
        super( code.getCode(), code.getMessage());
    }

    private ErrorResponse(ResultCode code, Exception e) {
        super(code.getCode(), code.getMessage(e));
    }

    private ErrorResponse(ResultCode code, String message) {
        super(code.getCode(), code.getMessage(message));
    }


    public static ErrorResponse of(ResultCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(ResultCode code, Exception e) {
        return new ErrorResponse(code, e);
    }

    public static ErrorResponse of(ResultCode code, String message) {
        return new ErrorResponse(code, message);
    }
}