package com.arronhuang.tiny.redis.handler;

import com.arronhuang.tiny.redis.enums.CommandEnum;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public interface ICommandHandler {

    RespResponse doHandle(List<String> args);

    void checkArgs(List<String> args);

    default void checkArgQty(String commandName, List<String> args) {
        CommandEnum commandEnum = RequestHandlerRegistry.getCommandEnum(commandName);
        int argQty = commandEnum.getArgQty();
        if (argQty >= 0) {
            AssertUtil.sizeEquals(args, argQty);
        } else {
            AssertUtil.sizeMoreThan(args, argQty * -1);
        }
    }

    default RespResponse handle(RespRequest request) {
        String commandName = request.getCommandName();
        List<String> args = request.getArgs();

        try {
            checkArgQty(commandName, args);
            checkArgs(args);
        } catch (Exception e) {
            // TODO 封装错误信息
        }

        RespResponse response = doHandle(args);
        return response;
    }

}
