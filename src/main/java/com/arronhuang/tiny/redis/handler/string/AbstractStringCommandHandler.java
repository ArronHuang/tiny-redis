package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.handler.CommandHandlerTemplate;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisString;

public abstract class AbstractStringCommandHandler extends CommandHandlerTemplate {

    /**
     * 根据 key 获取对应的 RedisString, 有可能返回 null
     *
     * @param key
     * @return
     */
    public RedisString get(String key, boolean createIfNotExists) {
        RedisString redisString = GlobalMap.getInstance().getValueByKeyAndType(key, RedisString.class);

        if (redisString == null && createIfNotExists) {
            redisString = new RedisString();
            GlobalMap.getInstance().put(key, redisString);
        }

        return redisString;
    }

    /**
     * 根据 key 获取对应的 RedisString 的值， 根据 returnBlankIfNotExists 决定当 RedisString 维护的值为空时, 返回空字符串还是 null
     *
     * @param key
     * @param returnBlankIfNotExists
     * @return
     */
    public String getString(String key, boolean returnBlankIfNotExists) {
        RedisString redisString = get(key, false);

        if (redisString == null) {
            return returnBlankIfNotExists ? "" : null;
        } else {
            return redisString.getValue();
        }
    }

    /**
     * 设置一个永久的键值对
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        GlobalMap.getInstance().put(key, new RedisString(value));
    }

    /**
     * 设置一个含过期时间的键值对, 传入的过期时间单位为 ms
     *
     * @param key
     * @param value
     * @param ttl
     */
    public void set(String key, Object value, int ttl) {
        GlobalMap.getInstance().put(key, new RedisString(value, ttl));
    }

    /**
     * 仅当传入的 key 不存在时, 设置一个永久的键值对
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfNotExists(String key, Object value) {
        return GlobalMap.getInstance().putIfAbsent(key, new RedisString(value)) == null;
    }

}
