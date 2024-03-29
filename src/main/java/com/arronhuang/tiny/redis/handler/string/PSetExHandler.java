package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public class PSetExHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        long ttl = Long.valueOf(args.get(1));
        String value = args.get(2);

        set(key, value, ttl, false);

        return RespResponse.ok();
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.isInteger(ErrorCodeEnum.VALUE_IS_NOT_AN_INTEGER_OR_OUT_OF_RANGE, args.get(1));
    }

}
