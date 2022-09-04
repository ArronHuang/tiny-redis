package com.arronhuang.tiny.redis.handler.set;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.RequestProcessException;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.RedisSet;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.ArrayList;
import java.util.List;

public class SRandMemberHandler extends AbstractSetCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisSet redisSet = get(key, false);

        if (args.size() == 1) {
            // 参数长度为1，则说明需要返回的值是一个string
            if (redisSet == null) {
                return RespResponse.bulkString(null);
            }
            String randomElement = redisSet.random();
            return RespResponse.bulkString(randomElement);
        } else {
            // 参数长度为2，则说明需要返回的值是一个array
            if (redisSet == null) {
                return RespResponse.emptyArray();
            }
            int count = Integer.valueOf(args.get(1));
            List<String> result = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                String randomElement = redisSet.random();
                if (randomElement != null) {
                    result.add(randomElement);
                } else {
                    break;
                }
            }
            return RespResponse.array(result);
        }
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.isTrue(ErrorCodeEnum.SYNTAX_ERROR, args.size() == 1 || args.size() == 2);

        try {
            Integer.valueOf(args.get(1));
        } catch (Exception e) {
            throw new RequestProcessException(ErrorCodeEnum.SYNTAX_ERROR);
        }

        AssertUtil.isPositiveInteger(args.get(1));
    }

}
