package com.arronhuang.tiny.redis.enums;

import com.arronhuang.tiny.redis.util.LogUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RespCommandTypeEnum {

    // common commands
    HELLO,
    COMMAND,
    PING,

    // commands for string
    SET,
    GET,
    INCR,
    DECR,

    ;

    public static RespCommandTypeEnum getTypeEnum(String commandType) {
        for (RespCommandTypeEnum typeEnum : values()) {
            if (commandType.equals(typeEnum.toString())) {
                return typeEnum;
            }
        }
        LogUtil.warn("no command type enum found, command type = {}", commandType);
        return null;
    }

}
