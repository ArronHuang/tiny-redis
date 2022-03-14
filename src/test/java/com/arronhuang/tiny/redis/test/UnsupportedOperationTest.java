package com.arronhuang.tiny.redis.test;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.AbstractCommandHandlerTemplate;
import com.arronhuang.tiny.redis.handler.string.GetDelHandler;
import com.arronhuang.tiny.redis.handler.string.GetExHandler;
import com.arronhuang.tiny.redis.handler.string.LcsHandler;
import com.arronhuang.tiny.redis.netty.RespRequest;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.test.util.JunitAssertUtil;
import org.junit.jupiter.api.Test;

public class UnsupportedOperationTest extends TestBase {

    private AbstractCommandHandlerTemplate lcsHandler = new LcsHandler();

    private AbstractCommandHandlerTemplate getExHandler = new GetExHandler();

    private AbstractCommandHandlerTemplate getDelHandler = new GetDelHandler();

    @Test
    public void testUnsupportedOperation() {
        doTestUnsupportedOperation("lcs", lcsHandler);
        doTestUnsupportedOperation("getex", getExHandler);
        doTestUnsupportedOperation("getdel", getDelHandler);
    }

    private void doTestUnsupportedOperation(String commandName, AbstractCommandHandlerTemplate handler) {
        RespRequest request = new RespRequest();
        request.setCommandName(commandName);
        RespResponse response = handler.handle(request);
        JunitAssertUtil.error(ErrorCodeEnum.OPERATION_IS_NOT_SUPPORTED_IN_THIS_VERSION, response);
    }

}
