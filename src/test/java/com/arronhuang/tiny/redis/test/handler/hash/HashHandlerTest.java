package com.arronhuang.tiny.redis.test.handler.hash;

import com.arronhuang.tiny.redis.handler.CommandHandlerTemplate;
import com.arronhuang.tiny.redis.handler.hash.*;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.arronhuang.tiny.redis.enums.CommandEnum.*;

public class HashHandlerTest extends HashHandlerTestBase {

    private CommandHandlerTemplate hSetHandler = new HSetHandler();

    private CommandHandlerTemplate hMSetHandler = new HMSetHandler();

    private CommandHandlerTemplate hDelHandler = new HDelHandler();

    private CommandHandlerTemplate hExistsHandler = new HExistsHandler();

    private CommandHandlerTemplate hGetHandler = new HGetHandler();

    private CommandHandlerTemplate hGetAllHandler = new HGetAllHandler();

    private CommandHandlerTemplate hIncrByFloatHandler = new HIncrByFloatHandler();

    private CommandHandlerTemplate hIncrByHandler = new HIncrByHandler();

    private CommandHandlerTemplate hKeysHandler = new HKeysHandler();

    private CommandHandlerTemplate hLenHandler = new HLenHandler();

    private CommandHandlerTemplate hMGetHandler = new HMGetHandler();

    private CommandHandlerTemplate hScanHandler = new HScanHandler();

    private CommandHandlerTemplate hStrLenHandler = new HStrLenHandler();

    private CommandHandlerTemplate hValsHandler = new HValsHandler();

    private CommandHandlerTemplate hSetNxHandler = new HSetNxHandler();

    @Test
    void testHSet() {
        RespRequest request = new RespRequest();
        request.setCommandName(HSET.name());

        request.setArgs("myhash", "field1", "Hello", "field2", "World");
        RespResponse response = hSetHandler.handle(request);
        JunitAssertUtil.number(2, response);
    }

    @Test
    void testHMSet() {
        RespRequest request = new RespRequest();
        request.setCommandName(HMSET.name());

        request.setArgs("myhash", "field1", "Hello", "field2", "World");
        RespResponse response = hMSetHandler.handle(request);
        JunitAssertUtil.ok(response);
    }

    @Test
    void testHDel() {
        put("myhash", "field1", "foo");

        RespRequest request = new RespRequest();
        request.setCommandName(HDEL.name());
        request.setArgs("myhash", "field1");
        RespResponse response = hDelHandler.handle(request);
        JunitAssertUtil.number(1, response);

        request.setArgs("myhash", "field2");
        response = hDelHandler.handle(request);
        JunitAssertUtil.number(0, response);

        request.setArgs("myhash1", "field2");
        response = hDelHandler.handle(request);
        JunitAssertUtil.number(0, response);

        assertFieldNotExists("myhash", "field1");
    }

    @Test
    void testHExists() {
        put("myhash", "field1", "foo");

        RespRequest request = new RespRequest();
        request.setCommandName(HEXISTS.name());
        request.setArgs("myhash", "field1");
        RespResponse response = hExistsHandler.handle(request);
        JunitAssertUtil.number(1, response);

        request.setArgs("myhash", "field2");
        response = hExistsHandler.handle(request);
        JunitAssertUtil.number(0, response);

        request.setArgs("myhash1", "field2");
        response = hExistsHandler.handle(request);
        JunitAssertUtil.number(0, response);
    }

    @Test
    void testHGet() {
        put("myhash", "field1", "foo");

        RespRequest request = new RespRequest();
        request.setCommandName(HGET.name());
        request.setArgs("myhash", "field1");
        RespResponse response = hGetHandler.handle(request);
        JunitAssertUtil.bulkString("foo", response);

        request.setArgs("myhash", "field2");
        response = hGetHandler.handle(request);
        JunitAssertUtil.bulkString(null, response);

        request.setArgs("myhash1", "field2");
        response = hGetHandler.handle(request);
        JunitAssertUtil.bulkString(null, response);
    }

    @Test
    void testHGetAll() {
        put("myhash", "field1", "hello", "field2", "world");

        RespRequest request = new RespRequest();
        request.setCommandName(HGETALL.name());
        request.setArgs("myhash");
        RespResponse response = hGetAllHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList("field1", "hello", "field2", "world"), response);

        request.setArgs("myhash1");
        response = hGetAllHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList(), response);
    }

    @Test
    void testHIncrBy() {
        put("myhash", "field", "5");

        RespRequest request = new RespRequest();
        request.setCommandName(HINCRBY.name());
        request.setArgs("myhash", "field", "1");
        RespResponse response = hIncrByHandler.handle(request);
        JunitAssertUtil.number(6, response);
        assertFieldExists("myhash", "field", "6");

        request.setArgs("myhash", "field", "-1");
        response = hIncrByHandler.handle(request);
        JunitAssertUtil.number(5, response);
        assertFieldExists("myhash", "field", "5");

        request.setArgs("myhash", "field", "-10");
        response = hIncrByHandler.handle(request);
        JunitAssertUtil.number(-5, response);
        assertFieldExists("myhash", "field", "-5");

        request.setArgs("myhash", "field1", "1");
        response = hIncrByHandler.handle(request);
        JunitAssertUtil.number(1, response);
        assertFieldExists("myhash", "field1", "1");
    }

    @Test
    void testHIncrByFloat() {
        put("mykey", "field", "10.50");

        RespRequest request = new RespRequest();
        request.setCommandName(HINCRBYFLOAT.name());
        request.setArgs("mykey", "field", "0.1");
        RespResponse response = hIncrByFloatHandler.handle(request);
        JunitAssertUtil.bulkString("10.6", response);
        assertFieldExists("mykey", "field", "10.6");

        request.setArgs("mykey", "field", "-5");
        response = hIncrByFloatHandler.handle(request);
        JunitAssertUtil.bulkString("5.6", response);
        assertFieldExists("mykey", "field", "5.6");

        put("mykey", "field", "5.0e3");
        request.setArgs("mykey", "field", "2.0e2");
        response = hIncrByFloatHandler.handle(request);
        JunitAssertUtil.bulkString("5200", response);
        assertFieldExists("mykey", "field", "5200");
    }

    @Test
    void testHKeys() {
        put("myhash", "field1", "hello", "field2", "world");

        RespRequest request = new RespRequest();
        request.setCommandName(HKEYS.name());
        request.setArgs("myhash");
        RespResponse response = hKeysHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList("field1", "field2"), response);

        request.setArgs("myhash1");
        response = hKeysHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList(), response);
    }

    @Test
    void testHLen() {
        put("myhash", "field1", "hello");

        RespRequest request = new RespRequest();
        request.setCommandName(HLEN.name());
        request.setArgs("myhash");
        RespResponse response = hLenHandler.handle(request);
        JunitAssertUtil.number(1, response);

        put("myhash", "field2", "world");
        request.setArgs("myhash");
        response = hLenHandler.handle(request);
        JunitAssertUtil.number(2, response);

        request.setArgs("myhash1");
        response = hLenHandler.handle(request);
        JunitAssertUtil.number(0, response);
    }

    @Test
    void testHScan() {
        RespRequest request = new RespRequest();
        request.setCommandName(HSCAN.name());
        request.setArgs("key", "cursor");
        RespResponse response = hScanHandler.handle(request);
        JunitAssertUtil.ok(response);
    }

    @Test
    void testHMGet() {
        put("myhash", "field1", "hello", "field2", "world");

        RespRequest request = new RespRequest();
        request.setCommandName(HMGET.name());
        request.setArgs("myhash", "field1", "field2", "nofield");
        RespResponse response = hMGetHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList("hello", "world", null), response);

        request.setCommandName(HMGET.name());
        request.setArgs("myhash1", "nofield1", "nofield2");
        response = hMGetHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList(null, null), response);
    }

    @Test
    void testHSetNx() {
        RespRequest request = new RespRequest();
        request.setCommandName(HSETNX.name());
        request.setArgs("myhash", "field", "hello");
        RespResponse response = hSetNxHandler.handle(request);
        JunitAssertUtil.number(1, response);

        request.setArgs("myhash", "field", "world");
        response = hSetNxHandler.handle(request);
        JunitAssertUtil.number(0, response);
    }

    @Test
    void testHStrLen() {
        put("myhash", "f1", "helloworld", "f2", "99", "f3", "-256");
        RespRequest request = new RespRequest();
        request.setCommandName(HSTRLEN.name());
        request.setArgs("myhash", "f1");
        RespResponse response = hStrLenHandler.handle(request);
        JunitAssertUtil.number(10, response);

        request.setArgs("myhash", "f2");
        response = hStrLenHandler.handle(request);
        JunitAssertUtil.number(2, response);

        request.setArgs("myhash", "f3");
        response = hStrLenHandler.handle(request);
        JunitAssertUtil.number(4, response);

        request.setArgs("myhash", "f4");
        response = hStrLenHandler.handle(request);
        JunitAssertUtil.number(0, response);
    }

    @Test
    void testHVals() {
        put("myhash", "field1", "hello", "field2", "world");
        RespRequest request = new RespRequest();
        request.setCommandName(HVALS.name());
        request.setArgs("myhash");
        RespResponse response = hValsHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList("hello", "world"), response);

        request.setArgs("myhash1");
        response = hValsHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList(), response);
    }

}
