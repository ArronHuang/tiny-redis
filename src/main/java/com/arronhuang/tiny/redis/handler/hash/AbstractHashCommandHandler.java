package com.arronhuang.tiny.redis.handler.hash;

import com.arronhuang.tiny.redis.handler.CommandHandlerTemplate;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisHash;

public abstract class AbstractHashCommandHandler extends CommandHandlerTemplate {

    /**
     * 根据 key 获取对应的 RedisList, 根据 createIfNotExists 决定当 RedisList 维护的值为空时, 是否创建 RedisList 对象
     *
     * @param key
     * @param createIfNotExists
     * @return
     */
    public RedisHash get(String key, boolean createIfNotExists) {
        RedisHash redisHash = GlobalMap.getInstance().get(key, RedisHash.class);

        if (redisHash == null && createIfNotExists) {
            redisHash = new RedisHash();
            GlobalMap.getInstance().put(key, redisHash);
        }

        return redisHash;
    }

}
