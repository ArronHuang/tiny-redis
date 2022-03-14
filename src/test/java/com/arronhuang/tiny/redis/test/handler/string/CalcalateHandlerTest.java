package com.arronhuang.tiny.redis.test.handler.string;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.AbstractCommandHandlerTemplate;
import com.arronhuang.tiny.redis.handler.string.*;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.arronhuang.tiny.redis.enums.CommandEnum.*;

public class CalcalateHandlerTest extends StringHandlerTestBase {

    private AbstractCommandHandlerTemplate decrHandler = new DecrHandler();

    private AbstractCommandHandlerTemplate incrHandler = new IncrHandler();

    private AbstractCommandHandlerTemplate decrByHandler = new DecrByHandler();

    private AbstractCommandHandlerTemplate incrByHandler = new IncrByHandler();

    private AbstractCommandHandlerTemplate incrByFloatHandler = new IncrByFloatHandler();

    @Test
    public void testDecr() {
        put("mykey", "10");
        RespRequest request = new RespRequest();
        request.setCommandName(DECR.name());

        request.setArgs(Arrays.asList("mykey"));
        RespResponse response = decrHandler.handle(request);
        JunitAssertUtil.number(9, response);
        assertKeyValueExists("mykey", "9");

        put("mykey", "234293482390480948029348230948");
        response = decrHandler.handle(request);
        JunitAssertUtil.error(ErrorCodeEnum.VALUE_IS_NOT_AN_INTEGER_OR_OUT_OF_RANGE, response);
    }

    @Test
    public void testIncr() {
        RespRequest request = new RespRequest();
        request.setCommandName(INCR.name());

        request.setArgs(Arrays.asList("mykey"));
        RespResponse response = incrHandler.handle(request);
        JunitAssertUtil.number(1, response);
        assertKeyValueExists("mykey", "1");

        request.setArgs(Arrays.asList("mykey"));
        response = incrHandler.handle(request);
        JunitAssertUtil.number(2, response);
        assertKeyValueExists("mykey", "2");

        put("mykey", "234293482390480948029348230948");
        response = incrHandler.handle(request);
        JunitAssertUtil.error(ErrorCodeEnum.VALUE_IS_NOT_AN_INTEGER_OR_OUT_OF_RANGE, response);
    }

    @Test
    public void testDecrBy() {
        put("mykey", "10");
        RespRequest request = new RespRequest();
        request.setCommandName(DECRBY.name());

        request.setArgs(Arrays.asList("mykey", "3"));
        RespResponse response = decrByHandler.handle(request);
        JunitAssertUtil.number(7, response);
        assertKeyValueExists("mykey", "7");
    }

    @Test
    public void testIncrBy() {
        put("mykey", "10");
        RespRequest request = new RespRequest();
        request.setCommandName(INCRBY.name());

        request.setArgs(Arrays.asList("mykey", "5"));
        RespResponse response = incrByHandler.handle(request);
        JunitAssertUtil.number(15, response);
        assertKeyValueExists("mykey", "15");
    }

    @Test
    public void testIncrByFloat() {
        RespRequest request = new RespRequest();
        request.setCommandName(INCRBYFLOAT.name());

        request.setArgs(Arrays.asList("mykey", "10.50"));
        RespResponse response = incrByFloatHandler.handle(request);
        JunitAssertUtil.bulkString("10.5", response);
        assertKeyValueExists("mykey", "10.5");

        request.setArgs(Arrays.asList("mykey", "0.1"));
        response = incrByFloatHandler.handle(request);
        JunitAssertUtil.bulkString("10.6", response);
        assertKeyValueExists("mykey", "10.6");

        request.setArgs(Arrays.asList("mykey", "-5"));
        response = incrByFloatHandler.handle(request);
        JunitAssertUtil.bulkString("5.6", response);
        assertKeyValueExists("mykey", "5.6");

        put("mykey", "5.0e3");
        request.setArgs(Arrays.asList("mykey", "2.0e2"));
        response = incrByFloatHandler.handle(request);
        JunitAssertUtil.bulkString("5200", response);
        assertKeyValueExists("mykey", "5200");

        put("mykey", "abc");
        request.setArgs(Arrays.asList("mykey", "1"));
        response = incrByFloatHandler.handle(request);
        JunitAssertUtil.error(ErrorCodeEnum.VALUE_IS_NOT_A_VALID_FLOAT, response);
    }

}
