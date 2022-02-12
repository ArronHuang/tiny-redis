package com.arronhuang.tiny.redis.netty;

import com.arronhuang.tiny.redis.enums.CommandNameEnum;
import lombok.Data;

import java.util.List;

@Data
public class RespRequest {

    private CommandNameEnum commandType;

    private List<String> args;

}
