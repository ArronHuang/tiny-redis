package com.arronhuang.tiny.redis.handler;

import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.List;

public interface ICommandHandler {

    RespResponse doHandle(List<String> args);

    void checkArgs(List<String> args);

    default RespResponse handle(RespRequest request) {
        List<String> args = request.getArgs();
        try {
            checkArgs(args);
        } catch (Exception e) {
            // TODO 封装错误信息
        }
        RespResponse response = doHandle(args);
        return response;
    }

}
