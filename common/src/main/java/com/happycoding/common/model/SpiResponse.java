package com.happycoding.common.model;

import com.happycoding.common.constants.SpiConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SpiResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> SpiResponse<T> ok(){
        return ok(null);
    }

    public static <T> SpiResponse<T> ok(T data) {
        return ok(SpiConstants.SpiCodeMsg.success.getMsg(), data);
    }

    public static <T> SpiResponse<T> ok(String message, T data) {
        return new SpiResponse<>(SpiConstants.SpiCodeMsg.success.getCode(), message, data);
    }

    public static <T> SpiResponse<T> error(int code, String message, T data) {
        if(code == SpiConstants.SpiCodeMsg.success.getCode()){
            code = SpiConstants.SpiCodeMsg.error.getCode();
        }
        return new SpiResponse<>(code, message, data);
    }

    public static <T> SpiResponse<T> error(int code, String message) {
        return error(code, message, null);
    }

    public static <T> SpiResponse<T> error(String message) {
        return error(SpiConstants.SpiCodeMsg.error.getCode(), message, null);
    }

    public static <T> SpiResponse<T> error() {
        return error(SpiConstants.SpiCodeMsg.error.getCode(), SpiConstants.SpiCodeMsg.error.getMsg(), null);
    }
}
