package com.arronhuang.tiny.redis.storage;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.RequestProcessException;

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

    public <T> T getValueByKeyAndType(String key, Class<T> clazz) {
        RedisObject redisObject = get(key);

        if (redisObject == null) {
            return null;
        }

        if (redisObject.getClass() != clazz) {
            throw new RequestProcessException(ErrorCodeEnum.OPERATION_AGAINST_A_KEY_HOLDING_THE_WRONG_KIND_OF_VALUE);
        }

        return (T) redisObject;
    }

}
