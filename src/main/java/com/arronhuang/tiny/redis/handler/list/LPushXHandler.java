package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisList;

import java.util.List;

public class LPushXHandler extends AbstractListCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        List<String> elements = args.subList(1, args.size());
        RespResponse response;
        RedisList redisList = get(key, false);
        if (redisList == null) {
            response = RespResponse.number(0);
        } else {
            response = RespResponse.number(redisList.leftPush(elements));
        }
        return response;
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
