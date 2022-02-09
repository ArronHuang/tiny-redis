package com.arronhuang.tiny.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RespResponseTypeEnum {

    SIMPLE_STRING("+"),
    ERROR("-"),
    NUMBER(":"),
    BULK_STRING("$"),
    ARRAY("*"),
    ;

    private String code;

}
