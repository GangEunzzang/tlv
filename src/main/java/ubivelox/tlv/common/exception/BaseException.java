package ubivelox.tlv.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    private final ResultCode resultCode;

    public BaseException() {
        super(ResultCode.INTERNAL_ERROR.getMessage());
        this.resultCode = ResultCode.INTERNAL_ERROR;
    }

    public BaseException(String message) {
        super(ResultCode.INTERNAL_ERROR.getMessage(message));
        this.resultCode = ResultCode.INTERNAL_ERROR;
    }

    public BaseException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public BaseException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public BaseException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
    }

    public BaseException(Throwable cause, ResultCode resultCode) {
        super(cause);
        this.resultCode = resultCode;
    }
}
