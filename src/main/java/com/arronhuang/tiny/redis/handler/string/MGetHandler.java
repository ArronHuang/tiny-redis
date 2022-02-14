package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class MGetHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.ARRAY);

        for (String key : args) {
            String value = getValue(key, false);
            response.addArg(value);
        }

        return response;
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
