package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public class MSetNxHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        boolean result = true;

        for (int i = 0; i < args.size(); i += 2) {
            String key = args.get(i);
            String value = args.get(i + 1);

            result &= setIfNotExists(key, value);
        }

        return RespResponse.number(result ? 1 : 0);
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.sizeIsEvenNumber(args);
    }

}
