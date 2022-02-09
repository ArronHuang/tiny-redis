package com.arronhuang.tiny.redis.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisString implements RedisObject {

    private String value;

}
