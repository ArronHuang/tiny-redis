package com.arronhuang.tiny.redis.handler;

import cn.hutool.core.util.ClassUtil;
import com.arronhuang.tiny.redis.enums.CommandEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class RequestHandlerRegistry {

    private static Map</* command name */String, ICommandHandler> registryMap = new HashMap<>();

    private static Map</* command name */String, CommandEnum> commandEnumMap = new HashMap<>();

    public static ICommandHandler getHandler(String commandName) {
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
            String commandName = commandEnum.toString();
            for (Class<?> clazz : classSet) {
                if ((commandEnum.toString() + "Handler").equalsIgnoreCase(clazz.getSimpleName())) {
                    try {
                        ICommandHandler commandHandler = (ICommandHandler) clazz.newInstance();
                        RequestHandlerRegistry.registryMap.put(commandName.toUpperCase(), commandHandler);
                        RequestHandlerRegistry.commandEnumMap.put(commandName.toUpperCase(), commandEnum);
                        log.info("command handler mapping created, commandName = {}, handler = {}", commandName, commandHandler);
                    } catch (Exception e) {
                        log.warn("command handler create failed, commandName = {}", commandName, e);
                    }
                }
            }
        }

        log.info("command handler mapping init finished! size = {}", RequestHandlerRegistry.registryMap.size());
    }

}
