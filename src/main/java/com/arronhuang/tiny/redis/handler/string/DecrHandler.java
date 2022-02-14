package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public class DecrHandler extends AbstractCalculateHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);

        return RespResponse.number(incrementByLongOffset(key, -1));
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.isInteger(args.get(0));
    }

}
