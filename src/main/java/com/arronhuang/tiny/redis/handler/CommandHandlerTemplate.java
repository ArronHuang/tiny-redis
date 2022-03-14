package com.arronhuang.tiny.redis.handler;

import com.arronhuang.tiny.redis.enums.CommandEnum;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class CommandHandlerTemplate {

    public abstract RespResponse doHandle(List<String> args);

    public abstract void checkArgs(List<String> args);

    protected void checkArgQty(String commandName, List<String> args) {
        CommandEnum commandEnum = RequestHandlerFactory.getCommandEnum(commandName);
        Integer argQty = commandEnum.getArgQty();
        if (argQty == null) {
            // do nothing
        } else if (argQty >= 0) {
            AssertUtil.sizeEquals(args, argQty);
        } else {
            AssertUtil.sizeMoreThan(args, argQty * -1);
        }
    }

    public RespResponse handle(RespRequest request) {
        String commandName = request.getCommandName();
        List<String> args = request.getArgs();
        RespResponse response;

        try {
            checkArgQty(commandName, args);
            checkArgs(args);
            response = doHandle(args);
        } catch (Exception e) {
            response = RespResponse.error(e.getMessage());
            log.error("request process error: {}", request.toString(), e);
        }

        return response;
    }

}
