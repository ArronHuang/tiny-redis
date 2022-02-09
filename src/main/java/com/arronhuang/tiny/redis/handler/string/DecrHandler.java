package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.handler.ICommandHandler;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisObject;
import com.arronhuang.tiny.redis.storage.RedisString;

import java.util.List;

public class DecrHandler implements ICommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        RedisObject redisObject = GlobalMap.getInstance().get(key);
        RespResponse response = null;
        if (redisObject instanceof RedisString) {
            RedisString redisString = (RedisString) redisObject;
            String value = redisString.getValue();
            try {
                long newValue = Long.valueOf(value) - 1;
                redisString.setValue(String.valueOf(newValue));
                GlobalMap.getInstance().put(key, redisString);
                response = RespResponse.number(newValue);
            } catch (NumberFormatException e) {
                response = RespResponse.error("value is not number type");
            }
        }
        return response;
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
