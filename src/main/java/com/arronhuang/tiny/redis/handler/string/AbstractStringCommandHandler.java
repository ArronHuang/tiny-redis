package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.handler.CommandHandlerTemplate;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisString;

public abstract class AbstractStringCommandHandler extends CommandHandlerTemplate {

    public RedisString get(String key) {
        return GlobalMap.getInstance().getValueByKeyAndType(key, RedisString.class);
    }

    public RedisString getOrCreate(String key) {
        RedisString redisString = GlobalMap.getInstance().getValueByKeyAndType(key, RedisString.class);

        if (redisString == null) {
            redisString = new RedisString();
            GlobalMap.getInstance().put(key, redisString);
        }

        return redisString;
    }

    public String getString(String key, boolean returnBlankIfNull) {
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
