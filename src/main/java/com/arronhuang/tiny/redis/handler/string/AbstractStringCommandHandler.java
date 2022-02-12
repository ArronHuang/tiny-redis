package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.handler.ICommandHandler;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisObject;
import com.arronhuang.tiny.redis.storage.RedisString;

public abstract class AbstractStringCommandHandler implements ICommandHandler {

    public RedisString get(String key) {
        RedisObject redisObject = GlobalMap.getInstance().get(key);
        if (redisObject instanceof RedisString) {
            return ((RedisString) redisObject);
        }
        return null;
    }

    public String getValue(String key, boolean returnBlankIfNull) {
        RedisString redisString = get(key);

        if (redisString == null) {
            return returnBlankIfNull ? "" : null;
        } else {
            return redisString.getValue();
        }
    }

    public void set(String key, Object value) {
        GlobalMap.getInstance().put(key, new RedisString(value));
    }

}
