package com.arronhuang.tiny.redis.storage;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.Date;

@Data
public class RedisObject<T> {

    protected T value;

    protected Long expireTime;

    public boolean isExpire() {
        if (expireTime == null) {
            return false;
        }

        return DateUtil.compare(new Date(), new Date(expireTime)) > 0;
    }

}
