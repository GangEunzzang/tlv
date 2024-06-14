package ubivelox.tlv.common.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ubivelox.tlv.common.exception.ResultCode;

@RequiredArgsConstructor
@Getter
@ToString
public class APIResponse {

    private final Integer code;

    private final String message;

    public static APIResponse of(ResultCode code) {
        return new APIResponse(code.getCode(), code.getMessage());
    }

    public static APIResponse of(ResultCode resultCode, Exception e) {
        return new APIResponse(resultCode.getCode(), resultCode.getMessage(e));
    }

    public static APIResponse of(ResultCode resultCode, String message) {
        return new APIResponse(resultCode.getCode(), resultCode.getMessage(message));
    }

}
