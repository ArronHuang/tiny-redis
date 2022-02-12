package com.arronhuang.tiny.redis.netty;

import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RespResponse {

    private RespResponseTypeEnum respResponseTypeEnum;

    private List<Object> args = new ArrayList<>();

    public void addArg(Object obj) {
        args.add(obj);
    }

    public static RespResponse ok() {
        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.SIMPLE_STRING);
        response.addArg("OK");
        return response;
    }

    public static RespResponse number(long number) {
        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.NUMBER);
        response.addArg(number);
        return response;
    }

    public static RespResponse error(String msg) {
        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.ERROR);
        response.addArg(msg);
        return response;
    }

    public static RespResponse bulkString(String result) {
        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.BULK_STRING);
        response.addArg(result);
        return response;
    }

}
