package com.arronhuang.tiny.redis.test.handler.set;

import cn.hutool.core.collection.CollUtil;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisSet;
import com.arronhuang.tiny.redis.test.TestBase;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetHandlerTestBase extends TestBase {

    public void put(String key, String... members) {
        RedisSet redisSet = GlobalMap.getInstance().get(key, RedisSet.class);
        if (redisSet == null) {
            redisSet = new RedisSet();
        }

        for (int i = 0; i < members.length; i++) {
            redisSet.add(members[i]);
        }

        GlobalMap.getInstance().put(key, redisSet);
    }

    protected void assertMember(String key, String... members) {
        RedisSet redisSet = GlobalMap.getInstance().get(key, RedisSet.class);
        if (redisSet == null) {
            JunitAssertUtil.isTrue(members.length == 0);
        }
        List<String> actual = new ArrayList<>(redisSet.values());
        List<String> expected = new ArrayList<>(Arrays.asList(members));
        Collections.sort(actual);
        Collections.sort(expected);
        JunitAssertUtil.isTrue(CollUtil.isEqualList(actual, expected));
    }

}
