package com.arronhuang.tiny.redis.netty;

import lombok.Data;

import java.util.List;

@Data
public class RespRequest {

    private String commandName;

    private List<String> args;

}
