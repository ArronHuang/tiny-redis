package com.arronhuang.tiny.redis.handler.set;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisSet;

import java.util.List;

public class SAddHandler extends AbstractSetCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisSet redisSet = get(key, true);

        int count = 0;
        for (int i = 1; i < args.size(); i++) {
            String element = args.get(i);
            count += redisSet.add(element) ? 1 : 0;
        }
        return RespResponse.number(count);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
