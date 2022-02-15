package com.arronhuang.tiny.redis.test;

import cn.hutool.core.util.RandomUtil;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;

@Slf4j
public class TestBase {

    @BeforeEach
    public void beforeEach() {
        log.info("clear all data before test");
        GlobalMap.getInstance().clear();
    }

    protected String getRandomString() {
        return RandomUtil.randomString(10);
    }

}
