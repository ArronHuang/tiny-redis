package com.arronhuang.tiny.redis.test.handler.set;

import com.arronhuang.tiny.redis.handler.CommandHandlerTemplate;
import com.arronhuang.tiny.redis.handler.set.*;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.arronhuang.tiny.redis.enums.CommandEnum.*;

public class SetHandlerTest extends SetHandlerTestBase {

    private CommandHandlerTemplate sAddHandler = new SAddHandler();

    private CommandHandlerTemplate sCardHandler = new SCardHandler();

    private CommandHandlerTemplate sDiffHandler = new SDiffHandler();

    private CommandHandlerTemplate sDiffStoreHandler = new SDiffStoreHandler();

    private CommandHandlerTemplate sInterHandler = new SInterHandler();

    private CommandHandlerTemplate sInterStoreHandler = new SInterStoreHandler();

    private CommandHandlerTemplate sIsMemberHandler = new SIsMemberHandler();

    private CommandHandlerTemplate sMembersHandler = new SMembersHandler();

    private CommandHandlerTemplate sMoveHandler = new SMoveHandler();

    private CommandHandlerTemplate sPopHandler = new SPopHandler();

    private CommandHandlerTemplate sRandMemberHandler = new SRandMemberHandler();

    private CommandHandlerTemplate sRemHandler = new SRemHandler();

    private CommandHandlerTemplate sScanHandler = new SScanHandler();

    private CommandHandlerTemplate sUnionHandler = new SUnionHandler();

    private CommandHandlerTemplate sUnionStoreHandler = new SUnionStoreHandler();

    @Test
    public void testSAdd() {
        RespRequest request = new RespRequest();
        request.setCommandName(SADD.name());
        request.setArgs("myset", "hello");
        RespResponse response = sAddHandler.handle(request);
        JunitAssertUtil.number(1, response);

        request.setArgs("myset", "world");
        response = sAddHandler.handle(request);
        JunitAssertUtil.number(1, response);

        request.setArgs("myset", "world");
        response = sAddHandler.handle(request);
        JunitAssertUtil.number(0, response);
    }

    @Test
    public void testSCard() {
        put("myset", "hello", "world");

        RespRequest request = new RespRequest();
        request.setCommandName(SCARD.name());
        request.setArgs("myset");
        RespResponse response = sCardHandler.handle(request);
        JunitAssertUtil.number(2, response);

        request.setArgs("myset1");
        response = sCardHandler.handle(request);
        JunitAssertUtil.number(0, response);
    }

    @Test
    public void testSDiff() {
        put("key1", "a", "b", "c");
        put("key2", "c", "d", "e");

        RespRequest request = new RespRequest();
        request.setCommandName(SDIFF.name());
        request.setArgs("key1", "key2");
        RespResponse response = sDiffHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList("a", "b"), response);

        request.setArgs("key3", "key4");
        response = sDiffHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList(), response);

        assertMember("key1", "a", "b", "c");
        assertMember("key2", "c", "d", "e");
    }

    @Test
    public void testSDiffStore() {
        put("key1", "a", "b", "c");
        put("key2", "c", "d", "e");

        RespRequest request = new RespRequest();
        request.setCommandName(SDIFFSTORE.name());
        request.setArgs("key1", "key2");
        RespResponse response = sDiffStoreHandler.handle(request);
        JunitAssertUtil.number(2, response);

        request.setArgs("key3", "key4");
        response = sDiffStoreHandler.handle(request);
        JunitAssertUtil.number(0, response);

        assertMember("key1", "a", "b");
        assertMember("key2", "c", "d", "e");
    }

    @Test
    public void testSInter() {
        put("key1", "a", "b", "c");
        put("key2", "c", "d", "e");

        RespRequest request = new RespRequest();
        request.setCommandName(SINTER.name());
        request.setArgs("key1", "key2");
        RespResponse response = sInterHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList("c"), response);

        request.setArgs("key3", "key4");
        response = sInterHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList(), response);

        assertMember("key1", "a", "b", "c");
        assertMember("key2", "c", "d", "e");
    }

    @Test
    public void testSInterStore() {
        put("key1", "a", "b", "c");
        put("key2", "c", "d", "e");

        RespRequest request = new RespRequest();
        request.setCommandName(SINTERSTORE.name());
        request.setArgs("key1", "key2");
        RespResponse response = sInterStoreHandler.handle(request);
        JunitAssertUtil.number(1, response);

        request.setArgs("key3", "key4");
        response = sInterStoreHandler.handle(request);
        JunitAssertUtil.number(0, response);

        assertMember("key1", "c");
        assertMember("key2", "c", "d", "e");
    }

    @Test
    public void testSIsMember() {
        put("myset", "one");

        RespRequest request = new RespRequest();
        request.setCommandName(SISMEMBER.name());
        request.setArgs("myset", "one");
        RespResponse response = sIsMemberHandler.handle(request);
        JunitAssertUtil.number(1, response);

        request.setArgs("myset", "two");
        response = sIsMemberHandler.handle(request);
        JunitAssertUtil.number(0, response);

        request.setArgs("myset1", "two");
        response = sIsMemberHandler.handle(request);
        JunitAssertUtil.number(0, response);
    }

    @Test
    public void testSMembers() {
        put("myset", "hello", "world");

        RespRequest request = new RespRequest();
        request.setCommandName(SMEMBERS.name());
        request.setArgs("myset");
        RespResponse response = sMembersHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList("world", "hello"), response);

        request.setArgs("myset1");
        response = sMembersHandler.handle(request);
        JunitAssertUtil.array(Arrays.asList(), response);
    }

    @Test
    public void testSMove() {
        put("myset", "one", "two");
        put("myotherset", "three");

        RespRequest request = new RespRequest();
        request.setCommandName(SMOVE.name());
        request.setArgs("myset", "myotherset", "two");
        RespResponse response = sMoveHandler.handle(request);
        JunitAssertUtil.number(1, response);

        request.setArgs("myset1", "myotherset", "two");
        response = sMoveHandler.handle(request);
        JunitAssertUtil.number(0, response);

        assertMember("myset", "one");
        assertMember("myotherset", "two", "three");
    }

}
