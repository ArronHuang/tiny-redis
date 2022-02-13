package com.arronhuang.tiny.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public enum CommandNameEnum {

    // common commands
    HELLO,
    COMMAND,
    PING,

    // commands for string
    APPEND,
    DECR,
    DECR_BY,
    GET,
    GET_DEL,
    GET_EX,
    GET_RANGE,
    GET_SET,
    INCR,
    INCR_BY,
    INCR_BY_FLOAT,
    LCS,
    SET
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

    public static CommandNameEnum getTypeEnum(String commandType) {
        for (CommandNameEnum typeEnum : values()) {
            if (commandType.equalsIgnoreCase(typeEnum.toString().replace("_", ""))) {
                return typeEnum;
            }
        }
        log.warn("no command type enum found, command type = {}", commandType);
        return null;
    }

}
