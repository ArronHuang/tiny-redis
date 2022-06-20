package com.arronhuang.tiny.redis.handler.common;

import com.arronhuang.tiny.redis.enums.CommandEnum;
import com.arronhuang.tiny.redis.handler.CommandHandlerTemplate;
import com.arronhuang.tiny.redis.netty.RespResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandHandler extends CommandHandlerTemplate {

    @Override
    public RespResponse doHandle(List<String> args) {
        List<List> commandDetails = new ArrayList();

        for (CommandEnum command : CommandEnum.values()) {
            List commandDetail = new ArrayList();
            // name
            commandDetail.add(command.name().toLowerCase());
            // arity
            commandDetail.add(command.getArity());
            // flags
            List<String> flags = Arrays.stream(command.getFlagEnums())
                    .map(flag -> flag.name().toLowerCase())
                    .collect(Collectors.toList());
            commandDetail.add(flags);
            // first key
            commandDetail.add(command.getFirstKeyIndex());
            // last key
            commandDetail.add(command.getLastKeyIndex());
            // step
            commandDetail.add(command.getKeyStep());
            commandDetails.add(commandDetail);
        }

        return RespResponse.array(commandDetails);
    }

    @Override
    public void checkArgs(List<String> args) {

    }

}
