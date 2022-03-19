package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisString;

import java.util.List;

public class IncrHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisString redisString = get(key, true);
        return RespResponse.number(redisString.increment(1));
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
