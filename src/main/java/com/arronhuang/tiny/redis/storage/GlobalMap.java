package com.arronhuang.tiny.redis.storage;

import java.util.HashMap;

public class GlobalMap extends HashMap<String, RedisObject> {

    private GlobalMap() {
    }

    private static GlobalMap INSTANCE = new GlobalMap();

    public static GlobalMap getInstance() {
        return INSTANCE;
    }

    @Override
    public RedisObject get(Object key) {
        RedisObject redisObject = super.get(key);

        if (redisObject == null) {
            return null;
        }

        if (redisObject.isExpire()) {
            GlobalMap.getInstance().remove(key);
            return null;
        }

        return redisObject;
    }

}
