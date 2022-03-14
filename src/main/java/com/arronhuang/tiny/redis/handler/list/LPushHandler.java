package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisList;

import java.util.List;

public class LPushHandler extends AbstractListCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisList redisList = get(key);

        for (int i = 1; i < args.size(); i++) {
            String element = args.get(i);
            redisList.add(0, element);
        }

        return RespResponse.number(redisList.size());
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
