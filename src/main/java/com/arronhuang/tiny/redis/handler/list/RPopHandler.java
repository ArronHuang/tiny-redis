package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisList;

import java.util.List;

public class RPopHandler extends AbstractListCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisList redisList = getWithoutNull(key);
        return RespResponse.bulkString(redisList.rightPop());
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
