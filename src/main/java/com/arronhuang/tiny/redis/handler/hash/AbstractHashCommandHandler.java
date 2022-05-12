package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.handler.AbstractCommandHandler;
import com.arronhuang.tiny.redis.storage.RedisHash;

public abstract class AbstractHashCommandHandler extends AbstractCommandHandler {

    /**
     * 根据 key 获取对应的 RedisList, 根据 createIfNotExists 决定当 RedisList 维护的值为空时, 是否创建 RedisList 对象
     *
     * @param key
     * @param createIfNotExists
     * @return
     */
    public RedisHash get(String key, boolean createIfNotExists) {
        return get(key, createIfNotExists, RedisHash.class);
    }

}
