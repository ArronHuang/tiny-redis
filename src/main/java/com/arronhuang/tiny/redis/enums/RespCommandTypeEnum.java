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
    INCR_BY,
    DECR_BY,
    INCR_BY_FLOAT,
    APPEND
    //    SET_RANGE,
    //    GET_RANGE,
    //    SET_BIT,
    //    GET_BIT,
    //    M_SET,
    //    M_GET,
    //    SET_NX,
    //    M_SET_NX,
    //    P_SET_NX,
    //    GET_SET,
    //    STR_LEN,
    //    SET_EX,

    ;

    public static RespCommandTypeEnum getTypeEnum(String commandType) {
        for (RespCommandTypeEnum typeEnum : values()) {
            if (commandType.equals(typeEnum.toString().replace("_", ""))) {
                return typeEnum;
            }
        }
        LogUtil.warn("no command type enum found, command type = {}", commandType);
        return null;
    }

}
