package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SetNxHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String value = args.get(1);

        boolean result = setIfNotExists(key, value);

        return RespResponse.number(result ? 1 : 0);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
