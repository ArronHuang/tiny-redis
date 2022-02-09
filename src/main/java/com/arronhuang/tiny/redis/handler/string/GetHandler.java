package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import com.arronhuang.tiny.redis.handler.ICommandHandler;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisObject;
import com.arronhuang.tiny.redis.storage.RedisString;

import java.util.List;

public class GetHandler implements ICommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.BULK_STRING);

        RedisObject redisObject = GlobalMap.getInstance().get(key);
        if (redisObject == null) {
            return response;
        }
        if (redisObject instanceof RedisString) {
            RedisString redisString = (RedisString) redisObject;
            response.addArg(redisString.getValue());
        }
        return response;
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
