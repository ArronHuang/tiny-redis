package com.arronhuang.tiny.redis.test.handler.string;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.ICommandHandler;
import com.arronhuang.tiny.redis.handler.string.DecrHandler;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CalcalateHandlerTest extends StringHandlerTestBase {

    private ICommandHandler decrHandler = new DecrHandler();

    @Test
    public void testDecr() {
        put("mykey", "10");
        RespRequest request = new RespRequest();
        request.setCommandName("decr");

        request.setArgs(Arrays.asList("mykey"));
        RespResponse response = decrHandler.handle(request);
        System.out.println(response);
        JunitAssertUtil.number(9, response);

        put("mykey", "234293482390480948029348230948");
        response = decrHandler.handle(request);
        JunitAssertUtil.error(ErrorCodeEnum.VALUE_IS_NOT_AN_INTEGER_OR_OUT_OF_RANGE, response);
    }

}
