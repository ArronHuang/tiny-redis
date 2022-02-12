package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncrHandler extends AbstractCalculateHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);

        return RespResponse.number(incrementByLongOffset(key, 1));
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
