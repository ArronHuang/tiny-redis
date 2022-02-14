package com.arronhuang.tiny.redis.netty;

import cn.hutool.core.collection.CollUtil;
import com.arronhuang.tiny.redis.enums.RespResponseTypeEnum;
import com.arronhuang.tiny.redis.util.AssertUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.text.StrPool.*;
import static com.arronhuang.tiny.redis.enums.GlobalConstant.ASTERISK;
import static com.arronhuang.tiny.redis.enums.GlobalConstant.DOLLAR;

public class ByteBufConverter {

    public static RespRequest byteBufToRespRequest(ByteBuf byteBuf) {
        RespRequest request = new RespRequest();
        List<String> commandList = new ArrayList<>();

        // 1. confirm whether next byte is '*'
        checkNextByte(byteBuf, ASTERISK);

        // 2. read array length
        int arrayLength = readNextInt(byteBuf, C_CR);

        // 3. confirm whether next byte is '\n'
        checkNextByte(byteBuf, C_LF);

        for (int i = 0; i < arrayLength; i++) {
            // 4. confirm whether next byte is '$'
            checkNextByte(byteBuf, DOLLAR);

            // 5. read command length
            int commandLength = readNextInt(byteBuf, C_CR);

            // 6. confirm whether next byte is '\n'
            checkNextByte(byteBuf, C_LF);

            // 7. read command
            String command = readString(byteBuf, commandLength);
            commandList.add(command);

            // 8. confirm whether next byte is '\n'
            checkNextByte(byteBuf, C_CR);
            checkNextByte(byteBuf, C_LF);
        }

        // 9. assemble request
        String commandName = commandList.get(0).toUpperCase();

        request.setCommandName(commandName);
        request.setArgs(commandList.subList(1, commandList.size()));

        return request;
    }

    public static ByteBuf respResponseToByteBuf(RespResponse response) {
        RespResponseTypeEnum respResponseTypeEnum = response.getRespResponseTypeEnum();
        List<Object> args = response.getArgs();

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

    private static int readNextInt(ByteBuf byteBuf, char stopChar) {
        return Integer.valueOf(readString(byteBuf, stopChar));
    }

    private static String readString(ByteBuf byteBuf, char stopChar) {
        byte b;
        StringBuilder sb = new StringBuilder();
        while ((b = byteBuf.readByte()) != stopChar) {
            sb.append(new String(new byte[]{b}));
        }
        return sb.toString();
    }

    private static String readString(ByteBuf byteBuf, int length) {
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        return new String(bytes);
    }

    private static void checkNextByte(ByteBuf byteBuf, char expected) {
        byte b = byteBuf.readByte();
        AssertUtil.equals(b, (byte) expected);
    }

}
