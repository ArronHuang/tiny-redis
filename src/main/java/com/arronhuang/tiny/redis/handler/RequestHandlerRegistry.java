package com.arronhuang.tiny.redis.handler;

import com.arronhuang.tiny.redis.enums.RespCommandTypeEnum;
import com.arronhuang.tiny.redis.handler.common.CommandHandler;
import com.arronhuang.tiny.redis.handler.common.HelloHandler;
import com.arronhuang.tiny.redis.handler.common.PingHandler;
import com.arronhuang.tiny.redis.handler.string.DecrHandler;
import com.arronhuang.tiny.redis.handler.string.GetHandler;
import com.arronhuang.tiny.redis.handler.string.IncrHandler;
import com.arronhuang.tiny.redis.handler.string.SetHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.arronhuang.tiny.redis.enums.RespCommandTypeEnum.*;

public class RequestHandlerRegistry {

    private static Map<RespCommandTypeEnum, ICommandHandler> registryMap = new ConcurrentHashMap<>();

    static {
        registryMap.put(PING, new PingHandler());
        registryMap.put(HELLO, new HelloHandler());
        registryMap.put(COMMAND, new CommandHandler());

        registryMap.put(SET, new SetHandler());
        registryMap.put(GET, new GetHandler());
        registryMap.put(INCR, new IncrHandler());
        registryMap.put(DECR, new DecrHandler());


    }

    public static ICommandHandler getHandler(RespCommandTypeEnum commandType) {
        return registryMap.get(commandType);
    }

}
