package com.arronhuang.tiny.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandEnum {

    // commands for common
    COMMAND(0, false),
    PING(0, false),
    HELLO(0, false),

    // commands for string
    APPEND(2, true),
    DECR(1, true),
    DECRBY(2, true),
    GET(1, true),
    GETDEL(1, true),
    GETEX(-1, true),
    GETRANGE(3, true),
    GETSET(2, true),
    INCR(1, true),
    INCRBY(2, true),
    INCRBYFLOAT(2, true),
    LCS(-2, true),
    MGET(-1, true),
    MSET(-2, true),
    MSETNX(-2, true),
    PSETEX(3, true),
    SET(-2, true),
    SETEX(3, true),
    SETNX(2, true),
    SETRANGE(3, true),
    STRLEN(1, true),
    SUBSTR(3, true),

    // commands for list
    BLMOVE(5, false),
    BLMPOP(-4, false),
    BLPOP(-2, true),
    BRPOP(-2, true),
    BRPOPLPUSH(3, false),
    LINDEX(2, true),
    LINSERT(4, true),
    LLEN(1, true),
    LMOVE(4, false),
    LMPOP(-3, true),
    LPOP(-1, true),
    LPOS(-2, true),
    LPUSH(-2, true),
    LPUSHX(-2, true),
    LRANGE(3, true),
    LREM(3, true),
    LSET(3, true),
    LTRIM(3, true),
    RPOP(-1, true),
    RPOPLPUSH(2, false),
    RPUSH(-2, true),
    RPUSHX(-2, true),

    // commands for hash
    HDEL(-2, true),
    HEXISTS(2, true),
    HGET(2, true),
    HGETALL(1, true),
    HINCRBY(3, true),
    HINCRBYFLOAT(3, true),
    HKEYS(1, true),
    HLEN(1, true),
    HMGET(-2, true),
    HMSET(-3, true),
    HRANDFIELD(-1, true),
    HSCAN(-2, true),
    HSET(-3, true),
    HSETNX(3, true),
    HSTRLEN(2, true),
    HVALS(1, true),

    ;

    private int argQty;

    private boolean keyFirst;

}
