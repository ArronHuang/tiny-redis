package com.arronhuang.tiny.redis.netty;

import com.arronhuang.tiny.redis.enums.GlobalConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RespRequestHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        RespRequest request = null;

        try {
            request = ByteBufConverter.byteBufToRespRequest(byteBuf);
        } catch (IllegalArgumentException e) {
            RespResponse response = RespResponse.error(e.getMessage());
            ctx.writeAndFlush(ByteBufConverter.respResponseToByteBuf(response));
            return;
        } catch (Exception e) {
            log.error("unknown exception: ", e);
        }

        GlobalConstant.REQUEST_PROCESSOR_EXECUTOR.execute(new RespRequestProcessor(ctx, request));
    }


}
