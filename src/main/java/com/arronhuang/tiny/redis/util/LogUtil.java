package com.arronhuang.tiny.redis.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LogUtil {

    public static void debug(String msg, Object... args) {
        if (log.isDebugEnabled()) {
            log.debug(msg, args);
        }
    }

    public static void info(String msg, Object... args) {
        if (log.isInfoEnabled()) {
            log.info(msg, args);
        }
    }

    public static void warn(String msg, Object... args) {
        if (log.isWarnEnabled()) {
            log.warn(msg, args);
        }
    }

    public static void error(String msg, Object... args) {
        if (log.isErrorEnabled()) {
            log.error(msg, args);
        }
    }
}
