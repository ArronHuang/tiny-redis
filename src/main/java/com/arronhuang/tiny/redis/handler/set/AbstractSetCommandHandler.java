package com.arronhuang.tiny.redis.handler.set;

import com.arronhuang.tiny.redis.handler.AbstractCommandHandler;
import com.arronhuang.tiny.redis.storage.RedisSet;

public abstract class AbstractSetCommandHandler extends AbstractCommandHandler {

    /**
     * 根据 key 获取对应的 RedisSet, 根据 createIfNotExists 决定当 RedisSet 维护的值为空时, 是否创建 RedisSet 对象
     *
     * @param key
     * @param createIfNotExists
     * @return
     */
    public RedisSet get(String key, boolean createIfNotExists) {
        return get(key, createIfNotExists, RedisSet.class);
    }

}
