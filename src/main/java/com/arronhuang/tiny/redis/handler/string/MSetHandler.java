package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public class MSetHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        for (int i = 0; i < args.size(); i += 2) {
            String key = args.get(i);
            String value = args.get(i + 1);

            set(key, value, NO_TTL, false);
        }

        return RespResponse.ok();
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.sizeIsEvenNumber(args);
    }

}
