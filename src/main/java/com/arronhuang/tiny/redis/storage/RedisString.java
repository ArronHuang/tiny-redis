package com.arronhuang.tiny.redis.storage;

import lombok.Data;

@Data
public class RedisString extends RedisObject {

    private String value;

    public RedisString(Object value) {
        this.value = String.valueOf(value);
    }

    public RedisString(Object value, int ttl) {
        this.value = String.valueOf(value);
        this.expireTime = System.currentTimeMillis() + ttl;
    }

}
