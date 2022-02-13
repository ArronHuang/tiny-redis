package com.arronhuang.tiny.redis.handler;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RequestHandlerRegistry implements ApplicationContextAware {

    private static Map</* command name */String, ICommandHandler> registryMap = new HashMap<>();

    public static ICommandHandler getHandler(String commandName) {
        return registryMap.get(commandName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // get all command handler beans
        Collection<ICommandHandler> handlers = applicationContext.getBeansOfType(ICommandHandler.class).values();

        if (CollUtil.isEmpty(handlers)) {
            return;
        }

        // match commandNameEnum and register handlers
        for (ICommandHandler handler : handlers) {
            String commandName = handler.getClass().getSimpleName().replace("Handler", "").toUpperCase();
            RequestHandlerRegistry.registryMap.put(commandName, handler);
            log.info("command handler mapping created, commandName = {}, handler = {}", commandName, handler);
        }

        log.info("command handler mapping init finished! size = {}", RequestHandlerRegistry.registryMap.size());
    }

}
