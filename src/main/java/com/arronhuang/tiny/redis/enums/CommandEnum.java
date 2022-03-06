package com.arronhuang.tiny.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandEnum {

    // commands for string
    APPEND(2),
    DECR(1),
    DECRBY(2),
    GET(1),
    GETDEL(null),
    GETEX(null),
    GETRANGE(3),
    GETSET(2),
    INCR(1),
    INCRBY(2),
    INCRBYFLOAT(2),
    LCS(null),
    MGET(-1),
    MSET(-2),
    MSETNX(-2),
    PSETEX(3),
    SET(-2),
    SETEX(3),
    SETNX(2),
    SETRANGE(3),
    STRLEN(1),
    SUBSTR(3),
    ;

    private Integer argQty;

}
