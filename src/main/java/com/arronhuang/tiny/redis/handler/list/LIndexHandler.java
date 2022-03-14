package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisList;
import com.arronhuang.tiny.redis.util.IndexUtil;

import java.util.List;

public class LIndexHandler extends AbstractListCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        // fixme 当 index 超过 length 时, 需要返回空集合给客户端

        String key = args.get(0);
        RedisList redisList = getWithoutNull(key);

        String targetElementOrIndex = args.get(1);
        boolean findByIndex = true;

        try {
            Integer.valueOf(targetElementOrIndex);
        } catch (NumberFormatException nfe) {
            findByIndex = false;
        }

        RespResponse response;
        if (findByIndex) {
            int index = Integer.valueOf(targetElementOrIndex);
            index = IndexUtil.translateIndex(index, redisList.size());
            response = RespResponse.bulkString(redisList.getValue().get(index));
        } else {
            response = RespResponse.number(redisList.findFirstElement(targetElementOrIndex));
        }

        return response;
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
