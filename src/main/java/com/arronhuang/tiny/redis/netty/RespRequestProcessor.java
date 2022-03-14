package com.arronhuang.tiny.redis.netty;

import com.arronhuang.tiny.redis.handler.AbstractCommandHandlerTemplate;
import com.arronhuang.tiny.redis.handler.RequestHandlerFactory;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class RespRequestProcessor implements Runnable {

    private ChannelHandlerContext ctx;

    private RespRequest request;

    @Override
    public void run() {
        String commandName = request.getCommandName();
        AbstractCommandHandlerTemplate handler = RequestHandlerFactory.getHandler(commandName);
        if (handler == null) {
            log.warn("command is not supported: {}", request.toString());
            ctx.writeAndFlush(RespResponse.error("command is not supported").toByteBuf());
        } else {
            RespResponse response = handler.handle(request);
            ctx.writeAndFlush(response.toByteBuf());
        }
    }

}
