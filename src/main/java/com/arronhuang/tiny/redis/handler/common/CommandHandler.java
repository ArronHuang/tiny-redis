package com.arronhuang.tiny.redis.handler.common;

import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import com.arronhuang.tiny.redis.handler.AbstractCommandHandlerTemplate;
import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class CommandHandler extends AbstractCommandHandlerTemplate {

    @Override
    public RespResponse doHandle(List<String> args) {
        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.ARRAY);
        return response;
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
