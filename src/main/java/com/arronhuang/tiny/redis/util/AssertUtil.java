package com.arronhuang.tiny.redis.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;

import java.util.List;

public final class AssertUtil {

    public static void equals(Object actual, Object excepted) {
        Assert.isTrue(ObjectUtil.equals(actual, excepted), "[Assertion failed] - excepted " + excepted + ", but " + actual);
    }

    public static void sizeEquals(List args, int exceptSize) {
        Assert.isTrue(args.size() == exceptSize, "ERR wrong number of arguments for this command");
    }

    public static void sizeMoreThan(List args, int exceptSize) {
        Assert.isTrue(args.size() == exceptSize, "ERR wrong number of arguments for this command");
    }

    public static void sizeIsEvenNumber(List args) {
        Assert.isTrue(args.size() % 2 == 0, "ERR wrong number of arguments for this command");
    }

    public static void isInteger(String... strings) {
        boolean result = true;
        try {
            for (String string : strings) {
                Long.valueOf(string);
            }
        } catch (NumberFormatException e) {
            result = false;
        }
        Assert.isTrue(result, "ERR value is not an integer or out of range");
    }

    public static void isFloat(String str) {
        boolean result = true;
        try {
            Double.valueOf(str);
        } catch (NumberFormatException e) {
            result = false;
        }
        Assert.isTrue(result, "ERR value is not a valid float");
    }

}
