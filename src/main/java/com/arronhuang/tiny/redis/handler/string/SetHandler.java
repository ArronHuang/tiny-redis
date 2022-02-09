package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.handler.ICommandHandler;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisString;

import java.util.List;

public class SetHandler implements ICommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        GlobalMap.getInstance().put(args.get(0), new RedisString(args.get(1)));
        return RespResponse.ok();
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
