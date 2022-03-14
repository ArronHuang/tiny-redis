package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisList;

import java.util.List;

public class LLenHandler extends AbstractListCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisList redisList = get(key);

        int length = redisList == null ? 0 : redisList.size();

        return RespResponse.number(length);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
