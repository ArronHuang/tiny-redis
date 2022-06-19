package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisHash;

import java.util.List;

public class HGetHandler extends AbstractHashCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String fieldName = args.get(1);
        RedisHash redisHash = get(key, false);
        if (redisHash == null) {
            return RespResponse.bulkString(null);
        }

        String value = redisHash.get(fieldName);
        return RespResponse.bulkString(value);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
