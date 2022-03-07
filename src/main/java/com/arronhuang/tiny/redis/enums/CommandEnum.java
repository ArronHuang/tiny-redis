package com.arronhuang.tiny.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandEnum {

    // commands for common
    COMMAND(0),
    PING(0),
    HELLO(0),

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

    // commands for list
    BLMOVE(null),
    BLMPOP(null),
    BLPOP(-2),
    BRPOP(-2),
    BRPOPLPUSH(3),
    LINDEX(2),
    LINSERT(4),
    LLEN(1),
    LMOVE(4),
    LMPOP(-3),
    LPOP(-1),
    LPOS(-2),
    LPUSH(-2),
    LPUSHX(-2),
    LRANGE(3),
    LREM(3),
    LSET(3),
    LTRIM(3),
    RPOP(-1),
    RPOPLPUSH(2),
    RPUSH(-2),
    RPUSHX(-2),
    ;

    private Integer argQty;

}
