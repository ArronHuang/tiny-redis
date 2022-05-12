package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class SetHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        String value = args.get(1);

        // TODO 带参数部分待处理
        set(key, value, NO_TTL, false);

        return RespResponse.ok();
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
