package com.arronhuang.tiny.redis.enums;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GlobalConstant {

    public static final int DEFAULT_PORT = 6379;

    public static final char DOLLAR = '$';

    public static final char ASTERISK = '*';

    public static final String OK = "OK";

    public static final Executor REQUEST_PROCESSOR_EXECUTOR = Executors.newSingleThreadExecutor();

}
