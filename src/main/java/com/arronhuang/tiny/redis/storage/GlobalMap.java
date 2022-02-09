package com.arronhuang.tiny.redis.storage;

import java.util.HashMap;

public class GlobalMap extends HashMap<String, RedisObject> {

    private GlobalMap() {}

    private static GlobalMap INSTANCE = new GlobalMap();

    public static GlobalMap getInstance() {
        return INSTANCE;
    }

}
