package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class GetSetHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String oldValue = getString(key, false);
        String newValue = args.get(1);

        set(key, newValue, NO_TTL, false);

        return RespResponse.bulkString(oldValue);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
