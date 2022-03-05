package com.arronhuang.tiny.redis.netty;

import com.arronhuang.tiny.redis.handler.ICommandHandler;
import com.arronhuang.tiny.redis.handler.RequestHandlerRegistry;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RespRequestProcessor implements Runnable {

    private ChannelHandlerContext ctx;

    private RespRequest request;

    @Override
    public void run() {
        String commandName = request.getCommandName();
        ICommandHandler handler = RequestHandlerRegistry.getHandler(commandName);
        RespResponse response = handler.handle(request);
        ctx.writeAndFlush(response.toByteBuf());
    }

}
