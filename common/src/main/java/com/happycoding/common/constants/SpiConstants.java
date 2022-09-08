package com.happycoding.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SpiConstants {
    public interface SpiCode {
        int success = 1;
        int error = 0;

    }

    @AllArgsConstructor
    @Getter
    public enum SpiCodeMsg {
        success(SpiCode.success,"成功"),error(SpiCode.error,"失败")
        ;
        int code;
        String msg;

    }
}
