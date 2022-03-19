package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisList;

import java.util.List;

public class RPushHandler extends AbstractListCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisList redisList = getOrCreate(key);

        redisList.rightPush(args.subList(1, args.size()));
        return RespResponse.number(redisList.size());
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
