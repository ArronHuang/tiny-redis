package com.arronhuang.tiny.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.arronhuang.tiny.redis.enums.ErrorTypeEnum.ERR;
import static com.arronhuang.tiny.redis.enums.ErrorTypeEnum.WRONGTYPE;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    // global error code

    WRONG_NUMBER_OF_ARGUMENTS_FOR_COMMAND(ERR, "wrong number of arguments for command"),
    OPERATION_IS_NOT_SUPPORTED_IN_THIS_VERSION(ERR, "operation is not supported in this version"),
    OPERATION_AGAINST_A_KEY_HOLDING_THE_WRONG_KIND_OF_VALUE(WRONGTYPE, "operation against a key holding the wrong kind of value"),
    SYNTAX_ERROR(ERR, "syntax error"),

    // string error code
    VALUE_IS_NOT_AN_INTEGER_OR_OUT_OF_RANGE(ERR, "value is not an integer or out of range"),
    VALUE_IS_NOT_A_VALID_FLOAT(ERR, "value is not a valid float"),

    // hash error code
    HASH_VALUE_IS_NOT_AN_INTEGER(ERR, "hash value is not an integer"),

    // set error code
    VALUE_IS_OUT_OF_RANGE_MUST_BE_POSITIVE(ERR, "value is out of range, must be positive"),

    ;

    private ErrorTypeEnum errorType;

    private String msg;

    @Override
    public String toString() {
        return errorType.name() + " " + msg;
    }
}
