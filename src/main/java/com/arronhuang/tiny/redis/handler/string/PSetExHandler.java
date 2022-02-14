package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class PSetExHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        int ttl = Integer.valueOf(args.get(1));
        String value = args.get(2);

        set(key, value, ttl);

        return RespResponse.ok();
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
