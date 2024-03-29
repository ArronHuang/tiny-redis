package com.arronhuang.tiny.redis.handler;

import cn.hutool.core.util.ClassUtil;
import com.arronhuang.tiny.redis.enums.CommandEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class RequestHandlerFactory {

    private static Map</* command name */String, CommandHandlerTemplate> registryMap = new HashMap<>();

    private static Map</* command name */String, CommandEnum> commandEnumMap = new HashMap<>();

    public static CommandHandlerTemplate getHandler(String commandName) {
        return registryMap.get(commandName);
    }

    public static CommandEnum getCommandEnum(String commandName) {
        return commandEnumMap.get(commandName.toUpperCase());
    }

    static {
        // get all command handlers
        Set<Class<?>> classSet = ClassUtil.scanPackage("com.arronhuang.tiny.redis.handler")
                .stream()
                .filter(clazz -> clazz.getSimpleName().endsWith("Handler"))
                .collect(Collectors.toSet());

        for (CommandEnum commandEnum : CommandEnum.values()) {
            String commandName = commandEnum.name();
            for (Class<?> clazz : classSet) {
                if ((commandName + "Handler").equalsIgnoreCase(clazz.getSimpleName())) {
                    try {
                        CommandHandlerTemplate commandHandler = (CommandHandlerTemplate) clazz.newInstance();
                        RequestHandlerFactory.registryMap.put(commandName.toUpperCase(), commandHandler);
                        RequestHandlerFactory.commandEnumMap.put(commandName.toUpperCase(), commandEnum);
                        log.info("command handler mapping created, commandName = {}, handler = {}", commandName, commandHandler);
                    } catch (Exception e) {
                        log.warn("command handler create failed, commandName = {}", commandName, e);
                    }
                }
            }
        }

        log.info("command handler mapping init finished! size = {}", RequestHandlerFactory.registryMap.size());
    }

}
