package com.arronhuang.tiny.redis.handler.set;

import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public class SScanHandler extends AbstractSetCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        // TODO
        return RespResponse.ok();
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
