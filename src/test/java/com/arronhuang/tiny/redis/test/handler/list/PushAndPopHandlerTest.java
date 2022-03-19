package com.arronhuang.tiny.redis.test.handler.list;

import com.arronhuang.tiny.redis.handler.CommandHandlerTemplate;
import com.arronhuang.tiny.redis.handler.list.LPushHandler;
import com.arronhuang.tiny.redis.handler.list.LPushXHandler;
import com.arronhuang.tiny.redis.handler.list.RPushHandler;
import com.arronhuang.tiny.redis.handler.list.RPushXHandler;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;
import org.junit.jupiter.api.Test;

public class PushAndPopHandlerTest extends ListHandlerTestBase {

    private CommandHandlerTemplate lPushHandler = new LPushHandler();

    private CommandHandlerTemplate rPushHandler = new RPushHandler();

    private CommandHandlerTemplate lPushXHandler = new LPushXHandler();

    private CommandHandlerTemplate rPushXHandler = new RPushXHandler();


    @Test
    public void testLPush() {
        String key = "mylist";
        RespRequest request = new RespRequest();
        request.setCommandName("lpush");
        request.setArgs(key, "world");

        RespResponse response = lPushHandler.handle(request);
        JunitAssertUtil.number(1, response);

        request.setArgs(key, "hello");
        response = lPushHandler.handle(request);
        JunitAssertUtil.number(2, response);

        assertKeyAndElementsExists(key, "hello", "world");
    }

    @Test
    public void testRPush() {
        String key = "mylist";
        RespRequest request = new RespRequest();
        request.setCommandName("rpush");
        request.setArgs(key, "hello");

        RespResponse response = rPushHandler.handle(request);
        JunitAssertUtil.number(1, response);

        request.setArgs(key, "world");
        response = rPushHandler.handle(request);
        JunitAssertUtil.number(2, response);

        assertKeyAndElementsExists(key, "hello", "world");
    }

    @Test
    public void testLPushX() {
        String key = "mylist";
        String anotherKey = "myotherlist";

        set(key, "world");
        RespRequest request = new RespRequest();

        request.setCommandName("lpush");
        request.setArgs(key, "hello");
        RespResponse response = lPushXHandler.handle(request);
        JunitAssertUtil.number(2, response);

        request.setArgs(anotherKey, "hello");
        response = lPushXHandler.handle(request);
        JunitAssertUtil.number(0, response);

        assertKeyAndElementsExists(key, "hello", "world");
        assertKeyNotExists(anotherKey);
    }

    @Test
    public void testRPushX() {
        String key = "mylist";
        String anotherKey = "myotherlist";

        set(key, "hello");
        RespRequest request = new RespRequest();

        request.setCommandName("lpush");
        request.setArgs(key, "world");
        RespResponse response = rPushXHandler.handle(request);
        JunitAssertUtil.number(2, response);

        request.setArgs(anotherKey, "hello");
        response = rPushXHandler.handle(request);
        JunitAssertUtil.number(0, response);

        assertKeyAndElementsExists(key, "hello", "world");
        assertKeyNotExists(anotherKey);
    }

}
