package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisHash;

import java.util.List;

public class HDelHandler extends AbstractHashCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisHash redisHash = get(key, false);

        if (redisHash == null) {
            return RespResponse.number(0);
        }

        args = args.subList(1, args.size());
        int effectedQty = redisHash.removeFields(args);
        return RespResponse.number(effectedQty);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
