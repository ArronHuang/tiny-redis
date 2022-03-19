package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisList;

import java.util.List;

public class LPushHandler extends AbstractListCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisList redisList = get(key, true);
        return RespResponse.number(redisList.leftPush(args.subList(1, args.size())));
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
