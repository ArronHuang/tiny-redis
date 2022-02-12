package com.arronhuang.tiny.redis.storage;

import lombok.Data;

@Data
public class RedisString implements RedisObject {

    private String value;

    public RedisString(Object value) {
        this.value = String.valueOf(value);
    }

}
