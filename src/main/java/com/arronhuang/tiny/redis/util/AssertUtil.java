package com.arronhuang.tiny.redis.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.RequestProcessException;

import java.util.List;

public final class AssertUtil {

    public static void equals(Object actual, Object excepted) {
        Assert.isTrue(ObjectUtil.equals(actual, excepted), "[Assertion failed] - excepted " + excepted + ", but " + actual);
    }

    public static void sizeEquals(List args, int exceptSize) {
        int actualSize = CollUtil.size(args);
        if (actualSize != exceptSize) {
            throw new RequestProcessException(ErrorCodeEnum.WRONG_NUMBER_OF_ARGUMENTS_FOR_COMMAND);
        }
    }

    public static void sizeMoreThan(List args, int exceptSize) {
        int actualSize = CollUtil.size(args);
        if (actualSize < exceptSize) {
            throw new RequestProcessException(ErrorCodeEnum.WRONG_NUMBER_OF_ARGUMENTS_FOR_COMMAND);
        }
    }

    public static void sizeIsEvenNumber(List args) {
        int actualSize = CollUtil.size(args);
        if (actualSize % 2 != 0) {
            throw new RequestProcessException(ErrorCodeEnum.WRONG_NUMBER_OF_ARGUMENTS_FOR_COMMAND);
        }
    }

    public static void sizeIsOddNumber(List args) {
        int actualSize = CollUtil.size(args);
        if (actualSize % 2 == 0) {
            throw new RequestProcessException(ErrorCodeEnum.WRONG_NUMBER_OF_ARGUMENTS_FOR_COMMAND);
        }
    }

    public static void isInteger(ErrorCodeEnum errorCode, String... strings) {
        try {
            for (String string : strings) {
                Long.valueOf(string);
            }
        } catch (NumberFormatException e) {
            throw new RequestProcessException(errorCode);
        }
    }

    public static void isPositiveInteger(int number) {
        Assert.isTrue(number > 0);
    }

    public static void isFloat(String str) {
        try {
            Double.valueOf(str);
        } catch (NumberFormatException e) {
            throw new RequestProcessException(ErrorCodeEnum.VALUE_IS_NOT_A_VALID_FLOAT);
        }
    }

}
