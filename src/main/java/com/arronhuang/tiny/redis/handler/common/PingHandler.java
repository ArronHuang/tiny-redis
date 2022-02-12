package com.arronhuang.tiny.redis.handler.common;

import cn.hutool.core.collection.CollUtil;
import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import com.arronhuang.tiny.redis.handler.ICommandHandler;
import com.arronhuang.tiny.redis.netty.RespResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PingHandler implements ICommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        RespResponse response = new RespResponse();

        String responseMsg;
        if (CollUtil.isNotEmpty(args)) {
            response.setRespResponseTypeEnum(RespResponseTypeEnum.BULK_STRING);
            responseMsg = args.get(0);
        } else {
            response.setRespResponseTypeEnum(RespResponseTypeEnum.SIMPLE_STRING);
            responseMsg = "PONG";
        }
        response.addArg(responseMsg);

        return response;
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
