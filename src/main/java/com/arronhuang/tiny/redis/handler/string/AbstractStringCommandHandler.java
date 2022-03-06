package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.handler.AbstractCommandHandler;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisObject;
import com.arronhuang.tiny.redis.storage.RedisString;

public abstract class AbstractStringCommandHandler extends AbstractCommandHandler {

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

    public void set(String key, Object value, int ttl) {
        GlobalMap.getInstance().put(key, new RedisString(value, ttl));
    }

    public boolean setIfNotExists(String key, Object value) {
        return GlobalMap.getInstance().putIfAbsent(key, new RedisString(value)) == null;
    }

}
