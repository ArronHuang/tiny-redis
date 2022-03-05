package com.arronhuang.tiny.redis.test.handler.string;

import com.arronhuang.tiny.redis.handler.ICommandHandler;
import com.arronhuang.tiny.redis.handler.string.*;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetAndGetHandlerTest extends StringHandlerTestBase {

    private ICommandHandler setHandler = new SetHandler();

    private ICommandHandler mSetHandler = new MSetHandler();

    private ICommandHandler setNxHandler = new SetNxHandler();

    private ICommandHandler getHandler = new GetHandler();

    private ICommandHandler mGetHandler = new MGetHandler();

    private ICommandHandler setRangeHandler = new SetRangeHandler();

    private ICommandHandler subStrHandler = new SubStrHandler();

    private ICommandHandler getRangeHandler = new GetRangeHandler();

    private ICommandHandler strLenHandler = new StrLenHandler();

    private ICommandHandler appendHandler = new AppendHandler();

    @Test
    public void testSet() {
        RespRequest request = new RespRequest();
        request.setCommandName("set");

        List<String> args = request.getArgs();
        String key = getRandomString();
        String value = getRandomString();
        args.add(key);
        args.add(value);

        setHandler.handle(request);

        assertKeyValueExists(key, value);
    }

    @Test
    public void testMSet() {
        RespRequest request = new RespRequest();
        request.setCommandName("mset");

        List<String> args = request.getArgs();

        int batchSize = 10;
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (int i = 0; i < batchSize; i++) {
            String key = getRandomString();
            String value = getRandomString();
            keys.add(key);
            values.add(value);
            args.add(key);
            args.add(value);
        }

        mSetHandler.handle(request);

        for (int i = 0; i < batchSize; i++) {
            assertKeyValueExists(keys.get(i), values.get(i));
        }
    }

    @Test
    public void testSetNx() {
        RespRequest request = new RespRequest();
        request.setCommandName("setnx");

        List<String> args = request.getArgs();
        String key = getRandomString();
        String value = getRandomString();
        args.add(key);
        args.add(value);

        setNxHandler.handle(request);

        assertKeyValueExists(key, value);

        args.remove(1);
        args.add(getRandomString());

        setNxHandler.handle(request);

        assertKeyValueExists(key, value);
    }

    @Test
    public void testGet() {
        String key = getRandomString();
        String value = getRandomString();

        put(key, value);

        RespRequest request = new RespRequest();
        request.setCommandName("get");

        List<String> args = request.getArgs();
        args.add(key);

        RespResponse response = getHandler.handle(request);
        JunitAssertUtil.bulkString(value, response);
    }

    @Test
    public void testMGet() {
        int batchSize = 10;
        List<String> values = new ArrayList<>();

        RespRequest request = new RespRequest();
        request.setCommandName("mget");

        for (int i = 0; i < batchSize; i++) {
            String key = getRandomString();
            String value = getRandomString();
            values.add(value);
            put(key, value);
            request.getArgs().add(key);
        }

        RespResponse response = mGetHandler.handle(request);
        JunitAssertUtil.array(values, response);
    }

    @Test
    public void testSetRange1() {
        put("key1", "hello world");
        RespRequest request = new RespRequest();
        request.setCommandName("setrange");
        request.setArgs(Arrays.asList("key1", "6", "redis"));
        RespResponse response = setRangeHandler.handle(request);
        JunitAssertUtil.number(11, response);
        assertKeyValueExists("key1", "hello redis");
    }

    @Test
    public void testSetRange2() {
        RespRequest request = new RespRequest();
        request.setCommandName("setrange");
        request.setArgs(Arrays.asList("key2", "6", "redis"));
        RespResponse response = setRangeHandler.handle(request);
        JunitAssertUtil.number(11, response);
        assertKeyValueExists("key2", "\u0000\u0000\u0000\u0000\u0000\u0000redis");
    }

    @Test
    public void testSetRange3() {
        RespRequest request = new RespRequest();
        put("key3", "hello redis");
        request.setCommandName("setrange");
        request.setArgs(Arrays.asList("key3", "6", "j"));
        RespResponse response = setRangeHandler.handle(request);
        JunitAssertUtil.number(11, response);
        assertKeyValueExists("key3", "hello jedis");
    }

    @Test
    public void testSubStr() {
        put("mykey", "this is a string");
        RespRequest request = new RespRequest();
        request.setCommandName("substr");

        request.setArgs(Arrays.asList("mykey", "0", "3"));
        RespResponse response = subStrHandler.handle(request);
        JunitAssertUtil.bulkString("this", response);

        request.setArgs(Arrays.asList("mykey", "-3", "-1"));
        response = subStrHandler.handle(request);
        JunitAssertUtil.bulkString("ing", response);

        request.setArgs(Arrays.asList("mykey", "0", "-1"));
        response = subStrHandler.handle(request);
        JunitAssertUtil.bulkString("this is a string", response);

        request.setArgs(Arrays.asList("mykey", "10", "100"));
        response = subStrHandler.handle(request);
        JunitAssertUtil.bulkString("string", response);
    }

    @Test
    public void testGetRange() {
        put("mykey", "this is a string");
        RespRequest request = new RespRequest();
        request.setCommandName("getrange");

        request.setArgs(Arrays.asList("mykey", "0", "3"));
        RespResponse response = getRangeHandler.handle(request);
        JunitAssertUtil.bulkString("this", response);

        request.setArgs(Arrays.asList("mykey", "-3", "-1"));
        response = getRangeHandler.handle(request);
        JunitAssertUtil.bulkString("ing", response);

        request.setArgs(Arrays.asList("mykey", "0", "-1"));
        response = getRangeHandler.handle(request);
        JunitAssertUtil.bulkString("this is a string", response);

        request.setArgs(Arrays.asList("mykey", "10", "100"));
        response = getRangeHandler.handle(request);
        JunitAssertUtil.bulkString("string", response);
    }

    @Test
    public void testStrLen() {
        put("mykey", "hello world");
        RespRequest request = new RespRequest();
        request.setCommandName("strlen");

        request.setArgs(Arrays.asList("mykey"));
        RespResponse response = strLenHandler.handle(request);
        JunitAssertUtil.number(11, response);

        request.setArgs(Arrays.asList("nonexisting"));
        response = strLenHandler.handle(request);
        JunitAssertUtil.number(0, response);
    }

    @Test
    public void testAppend() {
        RespRequest request = new RespRequest();
        request.setCommandName("append");

        request.setArgs(Arrays.asList("mykey", "Hello"));
        RespResponse response = appendHandler.handle(request);
        JunitAssertUtil.number(5, response);

        request.setArgs(Arrays.asList("mykey", " World"));
        response = appendHandler.handle(request);
        JunitAssertUtil.number(11, response);

        assertKeyValueExists("mykey", "Hello World");
    }

}


