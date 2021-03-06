package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public class IncrByFloatHandler extends AbstractCalculateHandler{

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String offset = args.get(1);

        return RespResponse.bulkString(incrementByFloatOffset(key, offset));
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.isFloat(args.get(1));
    }

}
