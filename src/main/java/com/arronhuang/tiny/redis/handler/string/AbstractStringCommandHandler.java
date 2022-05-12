package com.arronhuang.tiny.redis.handler.string;

import cn.hutool.core.date.DateUtil;
import com.arronhuang.tiny.redis.handler.AbstractCommandHandler;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisString;

import java.util.Date;

public abstract class AbstractStringCommandHandler extends AbstractCommandHandler {

    /**
     * 根据 key 获取对应的 RedisString, 根据 createIfNotExists 决定当 RedisString 维护的值为空时, 是否创建 RedisString 对象
     *
     * @param key
     * @param createIfNotExists
     * @return
     */
    public RedisString get(String key, boolean createIfNotExists) {
        return get(key, createIfNotExists, RedisString.class);
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
     * 设置一个含过期时间的键值对, 传入的过期时间单位为 ms
     *
     * @param key
     * @param value
     * @param ttl
     */
    public boolean set(String key, Object value, Long ttl, boolean setIfAbsent) {
        RedisString redisString = new RedisString();
        redisString.setValue(String.valueOf(value));
        if (ttl != null && ttl > 0) {
            redisString.setExpireTime(DateUtil.offsetMillisecond(new Date(), ttl.intValue()).getTime());
        }

        GlobalMap globalMap = GlobalMap.getInstance();
        if (setIfAbsent) {
            return globalMap.putIfAbsent(key, redisString) == null;
        } else {
            globalMap.put(key, redisString);
            return true;
        }
    }

}
