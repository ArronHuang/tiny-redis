package com.arronhuang.tiny.redis.handler;

import com.arronhuang.tiny.redis.handler.string.AbstractStringCommandHandler;
import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class AppendHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String appendContent = args.get(1);

        String oldValue = getValue(key, true);
        String newValue =  oldValue + appendContent;
        set(key, newValue);

        return RespResponse.number(newValue.length());
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
