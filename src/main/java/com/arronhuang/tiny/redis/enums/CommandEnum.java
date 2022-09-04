package com.arronhuang.tiny.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.arronhuang.tiny.redis.enums.FlagEnum.*;

@AllArgsConstructor
@Getter
public enum CommandEnum {


    // commands for common
    COMMAND(0, new FlagEnum[]{RANDOM, LOADING, STALE}, 0, 0, 0),
    PING(-1, new FlagEnum[]{STALE, FAST}, 0, 0, 0),

    // commands for string
    APPEND(3, new FlagEnum[]{WRITE, DENYOOM}, 1, 1, 1),
    DECR(2, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    DECRBY(3, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    GET(2, new FlagEnum[]{READONLY, FAST}, 1, 1, 1),
    GETRANGE(4, new FlagEnum[]{READONLY}, 1, 1, 1),
    GETSET(3, new FlagEnum[]{WRITE, DENYOOM}, 1, 1, 1),
    INCR(2, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    INCRBY(3, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    INCRBYFLOAT(3, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    MGET(-2, new FlagEnum[]{READONLY, FAST}, 1, -1, 1),
    MSET(-3, new FlagEnum[]{WRITE, DENYOOM}, 1, -1, 2),
    MSETNX(-3, new FlagEnum[]{WRITE, DENYOOM}, 1, -1, 2),
    PSETEX(4, new FlagEnum[]{WRITE, DENYOOM}, 1, 1, 1),
    SET(-3, new FlagEnum[]{WRITE, DENYOOM}, 1, 1, 1),
    SETEX(4, new FlagEnum[]{WRITE, DENYOOM}, 1, 1, 1),
    SETNX(3, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    SETRANGE(4, new FlagEnum[]{WRITE, DENYOOM}, 1, 1, 1),
    STRLEN(2, new FlagEnum[]{READONLY, FAST}, 1, 1, 1),
    SUBSTR(4, new FlagEnum[]{READONLY}, 1, 1, 1),

    // commands for list
    BLPOP(-3, new FlagEnum[]{WRITE, NOSCRIPT}, 1, -2, 1),
    BRPOP(-3, new FlagEnum[]{WRITE, NOSCRIPT}, 1, -2, 1),
    BRPOPLPUSH(4, new FlagEnum[]{WRITE, DENYOOM, NOSCRIPT}, 1, 2, 1),
    LINDEX(3, new FlagEnum[]{READONLY}, 1, 1, 1),
    LINSERT(5, new FlagEnum[]{WRITE, DENYOOM}, 1, 1, 1),
    LLEN(2, new FlagEnum[]{READONLY, FAST}, 1, 1, 1),
    LPOP(2, new FlagEnum[]{WRITE, FAST}, 1, 1, 1),
    LPUSH(-3, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    LPUSHX(-3, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    LRANGE(4, new FlagEnum[]{READONLY}, 1, 1, 1),
    LREM(4, new FlagEnum[]{WRITE}, 1, 1, 1),
    LSET(4, new FlagEnum[]{WRITE, DENYOOM}, 1, 1, 1),
    LTRIM(4, new FlagEnum[]{WRITE}, 1, 1, 1),
    RPOP(2, new FlagEnum[]{WRITE, FAST}, 1, 1, 1),
    RPOPLPUSH(3, new FlagEnum[]{WRITE, DENYOOM}, 1, 2, 1),
    RPUSH(-3, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    RPUSHX(-3, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),

    // commands for hash
    HDEL(-3, new FlagEnum[]{WRITE, FAST}, 1, 1, 1),
    HEXISTS(3, new FlagEnum[]{READONLY, FAST}, 1, 1, 1),
    HGET(3, new FlagEnum[]{READONLY, FAST}, 1, 1, 1),
    HGETALL(2, new FlagEnum[]{READONLY, RANDOM}, 1, 1, 1),
    HINCRBY(4, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    HINCRBYFLOAT(4, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    HKEYS(2, new FlagEnum[]{READONLY, SORT_FOR_SCRIPT}, 1, 1, 1),
    HLEN(2, new FlagEnum[]{READONLY, FAST}, 1, 1, 1),
    HMGET(-3, new FlagEnum[]{READONLY, FAST}, 1, 1, 1),
    HMSET(-4, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    HSCAN(-3, new FlagEnum[]{READONLY, RANDOM}, 1, 1, 1),
    HSET(-4, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    HSETNX(4, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    HSTRLEN(3, new FlagEnum[]{READONLY, FAST}, 1, 1, 1),
    HVALS(2, new FlagEnum[]{READONLY, SORT_FOR_SCRIPT}, 1, 1, 1),

    // commands for set
    SADD(-3, new FlagEnum[]{WRITE, DENYOOM, FAST}, 1, 1, 1),
    SCARD(2, new FlagEnum[]{READONLY, FAST}, 1, 1, 1),
    SDIFF(-2, new FlagEnum[]{READONLY, SORT_FOR_SCRIPT}, 1, -1, 1),
    SDIFFSTORE(-3, new FlagEnum[]{WRITE, DENYOOM}, 1, -1, 1),
    SINTER(-2, new FlagEnum[]{READONLY, SORT_FOR_SCRIPT}, 1, -1, 1),
    SINTERSTORE(-3, new FlagEnum[]{WRITE, DENYOOM}, 1, -1, 1),
    SISMEMBER(3, new FlagEnum[]{READONLY, FAST}, 1, 1, 1),
    SMEMBERS(2, new FlagEnum[]{READONLY, SORT_FOR_SCRIPT}, 1, 1, 1),
    SMOVE(4, new FlagEnum[]{WRITE, FAST}, 1, 2, 1),
    SPOP(-2, new FlagEnum[]{WRITE, RANDOM, FAST}, 1, 1, 1),
    SRANDMEMBER(-2, new FlagEnum[]{READONLY, RANDOM}, 1, 1, 1),
    SREM(-3, new FlagEnum[]{WRITE, FAST}, 1, 1, 1),
    SSCAN(-3, new FlagEnum[]{READONLY, RANDOM}, 1, 1, 1),
    SUNION(-2, new FlagEnum[]{READONLY, SORT_FOR_SCRIPT}, 1, -1, 1),
    SUNIONSTORE(-3, new FlagEnum[]{WRITE, DENYOOM}, 1, -1, 1),
    ;

    private int arity;

    private FlagEnum[] flagEnums;

    private int firstKeyIndex;

    private int lastKeyIndex;

    private int keyStep;

}
