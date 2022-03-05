package com.arronhuang.tiny.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    // global error code
    OPERATION_IS_NOT_SUPPORTED_IN_THIS_VERSION("operation is not supported in this version"),

    // string error code
    VALUE_IS_NOT_AN_INTEGER_OR_OUT_OF_RANGE("value is not an integer or out of range"),
    VALUE_IS_NOT_A_VALID_FLOAT("value is not a valid float"),

    ;

    private String msg;

}
