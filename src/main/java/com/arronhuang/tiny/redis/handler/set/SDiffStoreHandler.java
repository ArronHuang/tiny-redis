package com.arronhuang.tiny.redis.handler.set;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisSet;

import java.util.List;

public class SDiffStoreHandler extends AbstractSetCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisSet redisSet = get(key, false);

        if (redisSet == null) {
            return RespResponse.number(0);
        }

        for (int i = 1; i < args.size(); i++) {
            String otherKey = args.get(i);
            RedisSet otherRedisSet = get(otherKey, false);
            redisSet.removeAll(otherRedisSet);
        }
        return RespResponse.number(redisSet.size());
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
