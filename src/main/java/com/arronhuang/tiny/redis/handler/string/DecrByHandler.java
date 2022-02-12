package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class DecrByHandler extends AbstractCalculateHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        long offset = Long.valueOf(args.get(1)) * -1;

        return RespResponse.number(incrementByLongOffset(key, offset));
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
