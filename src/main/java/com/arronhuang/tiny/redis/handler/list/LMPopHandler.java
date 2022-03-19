package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.RequestProcessException;
import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class LMPopHandler extends AbstractListCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        // TODO since redis 7.0
        throw new RequestProcessException(ErrorCodeEnum.OPERATION_IS_NOT_SUPPORTED_IN_THIS_VERSION);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
