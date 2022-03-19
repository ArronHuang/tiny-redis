package com.arronhuang.tiny.redis.test.handler.list;

import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisList;
import com.arronhuang.tiny.redis.test.TestBase;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class ListHandlerTestBase extends TestBase {

    public void assertKeyAndElementsExists(String key, String... elements) {
        RedisList redisList = GlobalMap.getInstance().get(key, RedisList.class);
        JunitAssertUtil.notNull(redisList);
        JunitAssertUtil.equals(new ArrayList<>(Arrays.asList(elements)), redisList.getValue());
    }

    public void assertKeyNotExists(String key) {
        RedisList redisList = GlobalMap.getInstance().get(key, RedisList.class);
        JunitAssertUtil.isNull(redisList);
    }

    public void set(String key, String... elements) {
        RedisList redisList = new RedisList(new ArrayList<>(Arrays.asList(elements)));
        GlobalMap.getInstance().put(key, redisList);
    }


}
