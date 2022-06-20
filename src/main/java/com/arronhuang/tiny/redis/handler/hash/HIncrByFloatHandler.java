package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisHash;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public class HIncrByFloatHandler extends AbstractHashCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String fieldName = args.get(1);
        String offset = args.get(2);
        RedisHash redisHash = get(key, true);

        String newHashValue = redisHash.incrementByFloat(fieldName, offset);
        return RespResponse.bulkString(newHashValue);
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.isFloat(args.get(2));
    }

}
