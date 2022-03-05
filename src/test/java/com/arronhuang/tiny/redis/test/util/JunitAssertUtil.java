package com.arronhuang.tiny.redis.test.util;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.enums.GlobalConstant;
import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import com.arronhuang.tiny.redis.netty.RespResponse;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class JunitAssertUtil {

    public static void notNull(Object object) {
        Assertions.assertNotNull(object);
    }

    public static void isTrue(boolean expression) {
        Assertions.assertTrue(expression);
    }

    public static void isNull(Object object) {
        Assertions.assertNull(object);
    }

    public static void bulkString(String expected, RespResponse response) {
        Assertions.assertTrue(response.getRespResponseTypeEnum() == RespResponseTypeEnum.BULK_STRING);
        Assertions.assertTrue(response.getArgs().size() == 1);
        Assertions.assertEquals(expected, response.getArgs().get(0));
    }

    public static void equals(Object expected, Object actual) {
        Assertions.assertEquals(expected, actual);
    }

    public static void array(List<String> expected, RespResponse response) {
        Assertions.assertTrue(response.getRespResponseTypeEnum() == RespResponseTypeEnum.ARRAY);
        Assertions.assertTrue(response.getArgs().size() == expected.size());
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i), response.getArgs().get(i));
        }
    }

    public static void number(long expected, RespResponse response) {
        Assertions.assertTrue(response.getRespResponseTypeEnum() == RespResponseTypeEnum.NUMBER);
        Assertions.assertEquals(response.getArgs().get(0), expected);
    }

    public static void error(ErrorCodeEnum errorCodeEnum, RespResponse response) {
        Assertions.assertTrue(response.getRespResponseTypeEnum() == RespResponseTypeEnum.ERROR);
        Assertions.assertEquals(response.getArgs().get(0), GlobalConstant.ERROR_PREFIX + errorCodeEnum.getMsg());
    }

}
