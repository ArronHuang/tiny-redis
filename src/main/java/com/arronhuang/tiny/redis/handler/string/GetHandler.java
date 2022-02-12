package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import com.arronhuang.tiny.redis.netty.RespResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);

        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.BULK_STRING);

        String value = getValue(key, false);
        if (value != null) {
            response.addArg(value);
        }

        return response;
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
