package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetExHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        // TODO since redis 6
        throw new UnsupportedOperationException("this operation will be supported in version 6.0");
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
