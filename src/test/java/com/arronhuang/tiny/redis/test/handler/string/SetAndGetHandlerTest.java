package com.arronhuang.tiny.redis.test.handler.string;

import com.arronhuang.tiny.redis.handler.CommandHandlerTemplate;
import com.arronhuang.tiny.redis.handler.string.*;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;
import com.arronhuang.tiny.redis.test.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.arronhuang.tiny.redis.enums.CommandEnum.*;

public class SetAndGetHandlerTest extends StringHandlerTestBase {

    private CommandHandlerTemplate setHandler = new SetHandler();

    private CommandHandlerTemplate mSetHandler = new MSetHandler();

    private CommandHandlerTemplate setNxHandler = new SetNxHandler();

    private CommandHandlerTemplate getHandler = new GetHandler();

    private CommandHandlerTemplate mGetHandler = new MGetHandler();

    private CommandHandlerTemplate setRangeHandler = new SetRangeHandler();

    private CommandHandlerTemplate subStrHandler = new SubStrHandler();

    private CommandHandlerTemplate getRangeHandler = new GetRangeHandler();

    private CommandHandlerTemplate strLenHandler = new StrLenHandler();

    private CommandHandlerTemplate appendHandler = new AppendHandler();

    private CommandHandlerTemplate setExHandler = new SetExHandler();

    private CommandHandlerTemplate pSetExHandler = new PSetExHandler();

    private CommandHandlerTemplate mSetNxHandler = new MSetNxHandler();

    private CommandHandlerTemplate getSetHandler = new GetSetHandler();

    @Test
    public void testSet() {
        RespRequest request = new RespRequest();
        request.setCommandName(SET.name());

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
        request.setCommandName(MSET.name());

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
        request.setCommandName(SETNX.name());

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
        request.setCommandName(GET.name());

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
        request.setCommandName(MGET.name());

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
    public void testSetRange() {
        put("key1", "hello world");
        RespRequest request = new RespRequest();
        request.setCommandName(SETRANGE.name());
        request.setArgs("key1", "6", "redis");
        RespResponse response = setRangeHandler.handle(request);
        JunitAssertUtil.number(11, response);
        assertKeyValueExists("key1", "hello redis");

        request.setArgs("key2", "6", "redis");
        response = setRangeHandler.handle(request);
        JunitAssertUtil.number(11, response);
        assertKeyValueExists("key2", "\u0000\u0000\u0000\u0000\u0000\u0000redis");

        put("key3", "hello redis");
        request.setCommandName(SETRANGE.name());
        request.setArgs("key3", "6", "j");
        response = setRangeHandler.handle(request);
        JunitAssertUtil.number(11, response);
        assertKeyValueExists("key3", "hello jedis");
    }

    @Test
    public void testSubStr() {
        put("mykey", "this is a string");
        RespRequest request = new RespRequest();
        request.setCommandName(SUBSTR.name());

        request.setArgs("mykey", "0", "3");
        RespResponse response = subStrHandler.handle(request);
        JunitAssertUtil.bulkString("this", response);

        request.setArgs("mykey", "-3", "-1");
        response = subStrHandler.handle(request);
        JunitAssertUtil.bulkString("ing", response);

        request.setArgs("mykey", "0", "-1");
        response = subStrHandler.handle(request);
        JunitAssertUtil.bulkString("this is a string", response);

        request.setArgs("mykey", "10", "100");
        response = subStrHandler.handle(request);
        JunitAssertUtil.bulkString("string", response);
    }

    @Test
    public void testGetRange() {
        put("mykey", "this is a string");
        RespRequest request = new RespRequest();
        request.setCommandName(GETRANGE.name());

        request.setArgs("mykey", "0", "3");
        RespResponse response = getRangeHandler.handle(request);
        JunitAssertUtil.bulkString("this", response);

        request.setArgs("mykey", "-3", "-1");
        response = getRangeHandler.handle(request);
        JunitAssertUtil.bulkString("ing", response);

        request.setArgs("mykey", "0", "-1");
        response = getRangeHandler.handle(request);
        JunitAssertUtil.bulkString("this is a string", response);

        request.setArgs("mykey", "10", "100");
        response = getRangeHandler.handle(request);
        JunitAssertUtil.bulkString("string", response);
    }

    @Test
    public void testStrLen() {
        put("mykey", "hello world");
        RespRequest request = new RespRequest();
        request.setCommandName(STRLEN.name());

        request.setArgs("mykey");
        RespResponse response = strLenHandler.handle(request);
        JunitAssertUtil.number(11, response);

        request.setArgs("nonexisting");
        response = strLenHandler.handle(request);
        JunitAssertUtil.number(0, response);
    }

    @Test
    public void testAppend() {
        RespRequest request = new RespRequest();
        request.setCommandName(APPEND.name());

        request.setArgs("mykey", "Hello");
        RespResponse response = appendHandler.handle(request);
        JunitAssertUtil.number(5, response);

        request.setArgs("mykey", " World");
        response = appendHandler.handle(request);
        JunitAssertUtil.number(11, response);

        assertKeyValueExists("mykey", "Hello World");
    }

    @Test
    public void testSetEx() {
        RespRequest request = new RespRequest();
        request.setCommandName(SETEX.name());

        request.setArgs("mykey", "1", "Hello");
        RespResponse response = setExHandler.handle(request);
        JunitAssertUtil.ok(response);
        assertKeyValueExists("mykey", "Hello");

        TestUtil.sleep(1000);
        assertKeyNotExists("mykey");
    }

    @Test
    public void testPSetEx() {
        RespRequest request = new RespRequest();
        request.setCommandName(PSETEX.name());

        request.setArgs("mykey", "500", "Hello");
        RespResponse response = pSetExHandler.handle(request);
        JunitAssertUtil.ok(response);
        assertKeyValueExists("mykey", "Hello");

        TestUtil.sleep(1000);
        assertKeyNotExists("mykey");
    }

    @Test
    public void testMSetNx() {
        RespRequest request = new RespRequest();
        request.setCommandName(MSETNX.name());

        request.setArgs("key1", "Hello", "key2", "there");
        RespResponse response = mSetNxHandler.handle(request);
        JunitAssertUtil.number(1, response);
        assertKeyValueExists("key1", "Hello");
        assertKeyValueExists("key2", "there");

        request.setArgs("key2", "new", "key3", "world");
        response = mSetNxHandler.handle(request);
        JunitAssertUtil.number(0, response);
        assertKeyValueExists("key1", "Hello");
        assertKeyValueExists("key2", "there");
        assertKeyValueExists("key3", "world");
    }

    @Test
    public void testGetSet() {
        put("mykey", "Hello");

        RespRequest request = new RespRequest();
        request.setCommandName(GETSET.name());

        request.setArgs("mykey", "World");
        RespResponse response = getSetHandler.handle(request);
        JunitAssertUtil.bulkString("Hello", response);
        assertKeyValueExists("mykey", "World");
    }

}


