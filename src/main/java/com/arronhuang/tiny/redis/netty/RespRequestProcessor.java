package com.arronhuang.tiny.redis.netty;

import com.arronhuang.tiny.redis.enums.CommandNameEnum;
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
        CommandNameEnum commandType = request.getCommandType();
        ICommandHandler handler = RequestHandlerRegistry.getHandler(commandType);
        RespResponse response;
        try {
            response = handler.handle(request);
        } catch (Exception e) {
            response = RespResponse.error(e.getMessage());
        }
        ctx.writeAndFlush(ByteBufConverter.respResponseToByteBuf(response));
    }

}
