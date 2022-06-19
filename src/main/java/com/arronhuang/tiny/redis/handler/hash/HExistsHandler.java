package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisHash;

import java.util.List;

public class HExistsHandler extends AbstractHashCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String fieldName = args.get(1);
        RedisHash redisHash = get(key, false);

        if (redisHash == null) {
            return RespResponse.number(0);
        }

        return RespResponse.number(redisHash.existsField(fieldName) ? 0 : 1);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
