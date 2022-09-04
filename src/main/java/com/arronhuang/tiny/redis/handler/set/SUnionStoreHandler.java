package com.arronhuang.tiny.redis.handler.set;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisSet;

import java.util.List;

public class SUnionStoreHandler extends AbstractSetCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisSet redisSet = get(key, false);

        if (redisSet == null) {
            return RespResponse.emptyArray();
        }

        for (int i = 1; i < args.size(); i++) {
            String otherKey = args.get(i);
            RedisSet otherRedisSet = get(otherKey, false);
            redisSet.outerJoin(otherRedisSet);
        }
        return RespResponse.array(redisSet.values());
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
