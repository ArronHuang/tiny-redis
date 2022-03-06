package com.arronhuang.tiny.redis.storage;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.Date;

@Data
public class RedisString extends RedisObject {

    private String value;

    public RedisString(Object value) {
        this.value = String.valueOf(value);
    }

    public RedisString(Object value, int ttl) {
        this.value = String.valueOf(value);
        this.expireTime = DateUtil.offsetMillisecond(new Date(), ttl).getTime();
    }

}
