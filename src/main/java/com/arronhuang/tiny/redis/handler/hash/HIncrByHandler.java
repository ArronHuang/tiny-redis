package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisHash;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public class HIncrByHandler extends AbstractHashCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String fieldName = args.get(1);
        int offset = Integer.valueOf(args.get(2));
        RedisHash redisHash = get(key, true);

        int newHashValue = redisHash.incrementBy(fieldName, offset);
        return RespResponse.number(newHashValue);
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.isInteger(ErrorCodeEnum.HASH_VALUE_IS_NOT_AN_INTEGER, args.get(2));
    }

}
