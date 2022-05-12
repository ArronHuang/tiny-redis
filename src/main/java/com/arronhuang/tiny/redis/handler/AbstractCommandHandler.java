package com.arronhuang.tiny.redis.handler;

import cn.hutool.core.util.ReflectUtil;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisObject;

public abstract class AbstractCommandHandler extends CommandHandlerTemplate {

    protected static Long NO_TTL = null;

    public <T extends RedisObject> T get(String key, boolean createIfNotExists, Class<T> clazz) {
        T redisObject = GlobalMap.getInstance().get(key, clazz);

        if (redisObject == null && createIfNotExists) {
            redisObject = ReflectUtil.newInstance(clazz);
            GlobalMap.getInstance().put(key, redisObject);
        }
        return redisObject;
    }


}
