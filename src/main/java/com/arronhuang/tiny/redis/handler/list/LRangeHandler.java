package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisList;
import com.arronhuang.tiny.redis.util.AssertUtil;
import com.arronhuang.tiny.redis.util.IndexUtil;

import java.util.List;

public class LRangeHandler extends AbstractListCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        // fixme 当 index 超过 length 时, 需要返回空集合给客户端
        String key = args.get(0);

        RedisList redisList = getWithoutNull(key);

        int length = redisList == null ? 0 :redisList.size();

        int from = Integer.valueOf(args.get(1));
        int to = Integer.valueOf(args.get(2));

        from = IndexUtil.translateIndex(from, length);
        to = IndexUtil.translateIndex(to, length);

        return RespResponse.array(redisList.getValue().subList(from, to + 1));
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.isInteger(args.get(1), args.get(2));
    }

}
