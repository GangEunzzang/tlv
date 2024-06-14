package ubivelox.tlv.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ubivelox.tlv.common.response.ErrorResponse;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected ErrorResponse handleBaseException(BaseException e) {
        return ErrorResponse.of(e.getResultCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ErrorResponse handleException(Exception e) {
        return ErrorResponse.of(ResultCode.INTERNAL_ERROR);
    }

}
