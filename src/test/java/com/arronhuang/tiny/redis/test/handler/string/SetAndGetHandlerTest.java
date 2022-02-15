package com.arronhuang.tiny.redis.test.handler.string;

import com.arronhuang.tiny.redis.handler.ICommandHandler;
import com.arronhuang.tiny.redis.handler.string.*;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisString;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SetAndGetHandlerTest extends StringHandlerTestBase {

    private ICommandHandler setHandler = new SetHandler();

    private ICommandHandler mSetHandler = new MSetHandler();

    private ICommandHandler setNxHandler = new SetNxHandler();

    private ICommandHandler getHandler = new GetHandler();

    private ICommandHandler mGetHandler = new MGetHandler();

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

    private void put(String key, String value) {
        GlobalMap.getInstance().put(key, new RedisString(value));
    }

}


