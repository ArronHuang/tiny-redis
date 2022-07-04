package com.arronhuang.tiny.redis.netty;

import cn.hutool.core.collection.CollUtil;
import com.arronhuang.tiny.redis.enums.GlobalConstant;
import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cn.hutool.core.text.StrPool.CRLF;

@Data
public class RespResponse {

    private RespResponseTypeEnum respResponseTypeEnum;

    private List<Object> args = new ArrayList<>();

    public void addArg(Collection elements) {
        if (CollUtil.isEmpty(elements)) {
            return;
        }
        for (Object element : elements) {
            args.add(element);
        }
    }

    public void addArg(Object obj) {
        args.add(obj);
    }

    public static RespResponse ok() {
        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.SIMPLE_STRING);
        response.addArg(GlobalConstant.OK);
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

    public static RespResponse array(List elements) {
        RespResponse response = new RespResponse();
        response.setRespResponseTypeEnum(RespResponseTypeEnum.ARRAY);
        response.addArg(elements);
        return response;
    }

    public static RespResponse emptyArray() {
        return array(null);
    }

    public ByteBuf toByteBuf() {
        RespResponseTypeEnum respResponseTypeEnum = this.getRespResponseTypeEnum();
        List<Object> args = this.getArgs();

        StringBuilder sb = new StringBuilder();

        switch (respResponseTypeEnum) {
            case SIMPLE_STRING:
                sb.append(RespResponseTypeEnum.SIMPLE_STRING.getCode())
                        .append(args.get(0))
                        .append(CRLF);
                break;
            case NUMBER:
                sb.append(RespResponseTypeEnum.NUMBER.getCode())
                        .append(args.get(0))
                        .append(CRLF);
                break;
            case ERROR:
                sb.append(RespResponseTypeEnum.ERROR.getCode())
                        .append(args.get(0))
                        .append(CRLF);
                break;
            case BULK_STRING:
                String bulkString = String.valueOf(args.get(0));
                handleBulkString(sb, bulkString);
                break;
            case ARRAY:
                handleArray(sb, args);
        }

        return Unpooled.copiedBuffer(sb.toString().getBytes());
    }

    private void handleArray(StringBuilder sb, List items) {
        sb.append(RespResponseTypeEnum.ARRAY.getCode());
        int arrayLength = items.size();
        sb.append(arrayLength)
                .append(CRLF);
        for (int i = 0; i < arrayLength; i++) {
            Object arrayItem = items.get(i);
            if (arrayItem == null || arrayItem instanceof String) {
                handleBulkString(sb, (String) arrayItem);
            } else if (arrayItem instanceof Integer) {
                sb.append(RespResponseTypeEnum.NUMBER.getCode())
                        .append(arrayItem)
                        .append(CRLF);
            } else if (arrayItem instanceof List) {
                handleArray(sb, (List) arrayItem);
            }
        }
    }

    private void handleBulkString(StringBuilder sb, String bulkString) {
        sb.append(RespResponseTypeEnum.BULK_STRING.getCode());
        if (bulkString == null) {
            sb.append(-1);
        } else {
            sb.append(bulkString.length())
                    .append(CRLF)
                    .append(bulkString);
        }
        sb.append(CRLF);
    }

}
