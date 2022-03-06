package com.arronhuang.tiny.redis.handler.common;

import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import com.arronhuang.tiny.redis.handler.AbstractCommandHandler;
import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.ArrayList;
import java.util.List;

public class HelloHandler extends AbstractCommandHandler {

    private static final String VERSION_3_FLAG = "3";

    @Override
    public RespResponse doHandle(List<String> args) {
        RespResponse response = new RespResponse();
        response.addArg("server");
        response.addArg("redis");
        response.addArg("version");
        response.addArg("5.0.0");
        response.addArg("proto");
        response.addArg(3);
        response.addArg("id");
        response.addArg(10);
        response.addArg("mode");
        response.addArg("standalone");
        response.addArg("role");
        response.addArg("master");
        response.addArg("modules");
        response.addArg(new ArrayList<>());

        response.setRespResponseTypeEnum(RespResponseTypeEnum.ARRAY);

        return response;
    }

    @Override
    public void checkArgs(List<String> args) {

    }

//    private RespResponse doHandlerVersion2(RespRequest request) {
//        return null;
//    }
//
//    private RespResponse doHandlerVersion3(RespRequest request) {
//        return null;
//    }

}
