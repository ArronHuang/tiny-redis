package com.arronhuang.tiny.redis.test.util;

public class TestUtil {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

}
