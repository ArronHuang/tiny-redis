package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.RequestProcessException;
import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class GetExHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        // TODO since redis 6
        throw new RequestProcessException(ErrorCodeEnum.OPERATION_IS_NOT_SUPPORTED_IN_THIS_VERSION);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
