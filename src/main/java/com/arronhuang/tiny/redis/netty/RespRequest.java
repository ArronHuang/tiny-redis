package com.arronhuang.tiny.redis.netty;

import com.arronhuang.tiny.redis.util.AssertUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.text.StrPool.C_CR;
import static cn.hutool.core.text.StrPool.C_LF;
import static com.arronhuang.tiny.redis.enums.GlobalConstant.ASTERISK;
import static com.arronhuang.tiny.redis.enums.GlobalConstant.DOLLAR;

@Data
public class RespRequest {

    private String commandName;

    private List<String> args;

    public static RespRequest fromByteBuf(ByteBuf byteBuf) {
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
