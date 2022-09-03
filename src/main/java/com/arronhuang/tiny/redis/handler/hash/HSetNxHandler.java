package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisHash;

import java.util.List;

public class HSetNxHandler extends AbstractHashCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String fieldName = args.get(1);
        String fieldValue = args.get(2);

        RedisHash redisHash = get(key, true);
        boolean result = redisHash.setIfAbsent(fieldName, fieldValue);
        return RespResponse.number(result ? 1 : 0);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
