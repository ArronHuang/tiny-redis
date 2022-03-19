package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisString;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public class DecrByHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        long offset = Long.valueOf(args.get(1)) * -1;
        RedisString redisString = get(key, true);
        return RespResponse.number(redisString.increment(offset));
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.isInteger(args.get(1));
    }

}
