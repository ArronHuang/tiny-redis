package com.arronhuang.tiny.redis.handler;

import cn.hutool.core.collection.CollUtil;
import com.arronhuang.tiny.redis.enums.CommandNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class RequestHandlerRegistry implements ApplicationContextAware {

    private static Map<CommandNameEnum, ICommandHandler> registryMap = new ConcurrentHashMap<>();

    public static ICommandHandler getHandler(CommandNameEnum commandType) {
        return registryMap.get(commandType);
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
            String commandName = handler.getClass().getSimpleName().replace("Handler", "");
            CommandNameEnum commandNameEnum = CommandNameEnum.getTypeEnum(commandName);
            if (commandNameEnum != null) {
                RequestHandlerRegistry.registryMap.put(commandNameEnum, handler);
            }
        }

        log.info("command handler mapping init finished! size = {}", RequestHandlerRegistry.registryMap.size());
    }

}
