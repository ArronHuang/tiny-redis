package com.arronhuang.tiny.redis;

import com.arronhuang.tiny.redis.netty.TinyRedisServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class TinyRedisBootstrap {

    public static void main(String[] args) {
        // init spring application context
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TinyRedisBootstrap.class);
        applicationContext.refresh();

        // start netty server
        TinyRedisServer.start();
    }

}
