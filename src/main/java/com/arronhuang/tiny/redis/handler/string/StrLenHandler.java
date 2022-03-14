package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class StrLenHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String value = getString(key, true);

        return RespResponse.number(value.length());
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
