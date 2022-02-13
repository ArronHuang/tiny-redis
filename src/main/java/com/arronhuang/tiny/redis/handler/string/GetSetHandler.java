package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetSetHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String oldValue = getValue(key, false);
        String newValue = args.get(1);

        set(key, newValue);

        return RespResponse.bulkString(oldValue);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}