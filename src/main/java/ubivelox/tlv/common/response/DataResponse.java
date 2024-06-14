package ubivelox.tlv.common.response;

import lombok.Getter;
import ubivelox.tlv.common.exception.ResultCode;

@Getter
public class DataResponse<T> extends APIResponse {

    private final T data;

    private DataResponse(T data) {
        super(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        this.data = data;
    }

    public DataResponse(T data, String message) {
        super(ResultCode.SUCCESS.getCode(), message);
        this.data = data;
    }

    public static <T> DataResponse<T> of(T data) {
        return new DataResponse<>(data);
    }

    public static <T> DataResponse<T> of(T data, String message) {
        return new DataResponse<>(data, message);
    }

    public static <T> DataResponse<T> empty() {
        return new DataResponse<>(null);
    }
}