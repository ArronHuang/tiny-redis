package com.arronhuang.tiny.redis.test.handler.list;

import com.arronhuang.tiny.redis.handler.list.AbstractListCommandHandler;
import com.arronhuang.tiny.redis.handler.list.LLenHandler;
import com.arronhuang.tiny.redis.test.TestBase;
import org.junit.jupiter.api.Test;

public class ReadHandlerTest extends TestBase {

    private AbstractListCommandHandler lLenHandler = new LLenHandler();

    @Test
    public void testLLen() {

    }

}
