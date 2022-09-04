package com.arronhuang.tiny.redis.handler.set;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisSet;

import java.util.List;

public class SIsMemberHandler extends AbstractSetCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String element = args.get(1);
        RedisSet redisSet = get(key, false);

        if (redisSet == null) {
            return RespResponse.number(0);
        }
        return RespResponse.number(redisSet.contains(element) ? 1 : 0);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
