package com.arronhuang.tiny.redis.test.handler.string;

import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisObject;
import com.arronhuang.tiny.redis.storage.RedisString;
import com.arronhuang.tiny.redis.test.TestBase;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;

public class StringHandlerTestBase extends TestBase {

    protected void assertKeyValueExists(String key, Object value) {
        RedisObject redisObject = GlobalMap.getInstance().get(key);

        JunitAssertUtil.notNull(redisObject);
        JunitAssertUtil.isTrue(redisObject instanceof RedisString);

        RedisString redisString = (RedisString) redisObject;
        JunitAssertUtil.equals(String.valueOf(redisString.getValue()), String.valueOf(value));
    }

    protected void assertKeyNotExists(String key) {
        RedisObject redisObject = GlobalMap.getInstance().get(key);
        JunitAssertUtil.isNull(redisObject);
    }

    protected void put(String key, String value) {
        RedisString redisString = new RedisString();
        redisString.setValue(value);
        GlobalMap.getInstance().put(key, redisString);
    }

}
