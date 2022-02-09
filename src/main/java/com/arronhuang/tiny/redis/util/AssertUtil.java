package com.arronhuang.tiny.redis.util;

import cn.hutool.core.lang.Assert;

public final class AssertUtil {

    public static void equals(byte actual, byte excepted) {
        Assert.isTrue(actual == excepted, "[Assertion failed] - excepted " + excepted + ", but " + actual);
    }

}
