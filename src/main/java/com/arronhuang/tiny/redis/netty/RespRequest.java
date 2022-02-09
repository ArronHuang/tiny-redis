package com.arronhuang.tiny.redis.netty;

import com.arronhuang.tiny.redis.enums.RespCommandTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class RespRequest {

    private RespCommandTypeEnum commandType;

    private List<String> args;

}
