package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisHash;

import java.util.List;

public class HGetAllHandler extends AbstractHashCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisHash redisHash = get(key, false);

        if (redisHash == null) {
            return RespResponse.array(null);
        }

        List<String> entries = redisHash.getAll();
        return RespResponse.array(entries);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
