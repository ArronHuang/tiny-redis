package com.arronhuang.tiny.redis.handler;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;

public class RequestProcessException extends RuntimeException {

    public RequestProcessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
    }

}
