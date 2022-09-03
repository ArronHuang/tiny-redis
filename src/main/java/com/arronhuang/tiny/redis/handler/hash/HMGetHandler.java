package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisHash;

import java.util.List;

public class HMGetHandler extends AbstractHashCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.ARRAY);

        String key = args.get(0);
        RedisHash redisHash = get(key, false);

        for (int i = 1; i < args.size(); i++) {
            String fieldName = args.get(i);

            if (redisHash == null) {
                response.addArg(null);
            } else {
                response.addArg(redisHash.get(fieldName));
            }
        }

        return response;
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
