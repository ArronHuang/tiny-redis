package com.arronhuang.tiny.redis.storage;

import lombok.Data;

import java.util.List;

@Data
public class RedisList extends RedisObject {

    private List<String> value;

}
