package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SetExHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        int ttl = Integer.valueOf(args.get(1));
        String value = args.get(2);

        set(key, value, ttl * 1000);

        return RespResponse.ok();
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}