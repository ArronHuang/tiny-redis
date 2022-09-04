package com.arronhuang.tiny.redis.handler.set;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisSet;

import java.util.List;

public class SMoveHandler extends AbstractSetCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String sourceKey = args.get(0);
        String destinationKey = args.get(1);
        String member = args.get(2);

        RedisSet sourceSet = get(sourceKey, false);
        if (sourceSet == null || !sourceSet.contains(member)) {
            return RespResponse.number(0);
        }

        sourceSet.remove(member);
        RedisSet destinationSet = get(destinationKey, true);
        destinationSet.add(member);
        return RespResponse.number(1);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
