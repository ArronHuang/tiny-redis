package com.arronhuang.tiny.redis.test.handler.hash;

import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisHash;
import com.arronhuang.tiny.redis.test.TestBase;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;

public class HashHandlerTestBase extends TestBase {

    protected void put(String key, String... fields) {
        RedisHash redisHash = GlobalMap.getInstance().get(key, RedisHash.class);
        if (redisHash == null) {
            redisHash = new RedisHash();
        }

        for (int i = 0; i < fields.length; i += 2) {
            redisHash.set(fields[i], fields[i + 1]);
        }

        GlobalMap.getInstance().put(key, redisHash);
    }

    protected void assertFieldNotExists(String key, String fieldName) {
        RedisHash redisHash = GlobalMap.getInstance().get(key, RedisHash.class);
        JunitAssertUtil.isTrue(redisHash == null || !redisHash.existsField(fieldName));
    }

    protected void assertFieldExists(String key, String fieldName, String fieldValue) {
        RedisHash redisHash = GlobalMap.getInstance().get(key, RedisHash.class);
        JunitAssertUtil.isTrue(redisHash != null || fieldValue.equals(redisHash.get(fieldName)));
    }

}
