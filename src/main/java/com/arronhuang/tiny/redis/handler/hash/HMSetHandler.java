package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisHash;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public class HMSetHandler extends AbstractHashCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisHash redisHash = get(key, true);

        for (int i = 1; i < args.size(); i += 2) {
            String fieldName = args.get(i);
            String fieldValue = args.get(i + 1);

            redisHash.set(fieldName, fieldValue);
        }

        return RespResponse.ok();
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.sizeIsOddNumber(args);
    }

}
