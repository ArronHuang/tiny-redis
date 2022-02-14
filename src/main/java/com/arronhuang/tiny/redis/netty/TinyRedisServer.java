package com.arronhuang.tiny.redis.netty;

import com.arronhuang.tiny.redis.enums.GlobalConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TinyRedisServer {

    public static void start(int port) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new TinyRedisServerHandler())
                        ;
                    }
                });
        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();
            if (future.isSuccess()) {
                log.info("Tiny Redis Server starts successful");
            } else {
                log.error("Tiny Redis Server starts failed");
            }
        } catch (InterruptedException e) {
            log.error("TinyRedisServer has been interrupted");
        }
    }

    private static boolean isLinux() {
        return System.getProperty("os.name").contains("linux");
    }

    public static void start() {
        start(GlobalConstant.DEFAULT_PORT);
    }

}
