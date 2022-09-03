package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisHash;

import java.util.List;

public class HStrLenHandler extends AbstractHashCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String fieldName = args.get(1);

        RedisHash redisHash = get(key, false);
        if (redisHash == null || redisHash.get(fieldName) == null) {
            return RespResponse.number(0);
        }

        return RespResponse.number(redisHash.get(fieldName).length());
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
