package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
