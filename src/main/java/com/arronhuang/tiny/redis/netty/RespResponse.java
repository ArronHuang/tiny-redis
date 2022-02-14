package com.arronhuang.tiny.redis.netty;

import cn.hutool.core.collection.CollUtil;
import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.text.StrPool.CRLF;

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

    public ByteBuf toByteBuf() {
        RespResponseTypeEnum respResponseTypeEnum = this.getRespResponseTypeEnum();
        List<Object> args = this.getArgs();

        StringBuilder sb = new StringBuilder();
        sb.append(respResponseTypeEnum.getCode());

        switch (respResponseTypeEnum) {
            case SIMPLE_STRING:
            case NUMBER:
            case ERROR:
                // response msg or number or error
                sb.append(args.get(0))
                        .append(CRLF);
                break;
            case BULK_STRING:
                if (CollUtil.isNotEmpty(args) && args.get(0) != null) {
                    String bulkString = String.valueOf(args.get(0));
                    sb.append(bulkString.length())
                            .append(CRLF)
                            .append(bulkString)
                            .append(CRLF);
                } else {
                    sb.append(-1)
                            .append(CRLF);
                }
                break;
            case ARRAY:
                int arrayLength = args.size();
                sb.append(arrayLength)
                        .append(CRLF);
                for (int i = 0; i < arrayLength; i++) {
                    Object arrayItem = args.get(i);
                    if (arrayItem == null) {
                        sb.append(RespResponseTypeEnum.BULK_STRING.getCode())
                                .append(-1)
                                .append(CRLF);
                    } else {
                        String str = String.valueOf(arrayItem);
                        sb.append(RespResponseTypeEnum.BULK_STRING.getCode())
                                .append(str.length())
                                .append(CRLF)
                                .append(str)
                                .append(CRLF);
                    }
                }
        }

        return Unpooled.copiedBuffer(sb.toString().getBytes());
    }

}
