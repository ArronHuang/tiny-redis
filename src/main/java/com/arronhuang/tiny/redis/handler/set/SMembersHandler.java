package com.arronhuang.tiny.redis.handler.set;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisSet;

import java.util.List;

public class SMembersHandler extends AbstractSetCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisSet redisSet = get(key, false);

        if (redisSet == null) {
            return RespResponse.emptyArray();
        }
        return RespResponse.array(redisSet.values());
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
