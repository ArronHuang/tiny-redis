package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.handler.CommandHandlerTemplate;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisList;

import java.util.LinkedList;

public abstract class AbstractListCommandHandler extends CommandHandlerTemplate {

    public RedisList get(String key) {
        return get(key, false);
    }

    public RedisList get(String key, boolean createIfNull) {
        RedisList redisList = GlobalMap.getInstance().getValueByKeyAndType(key, RedisList.class);

        if (redisList == null && createIfNull) {
            redisList = new RedisList();
            GlobalMap.getInstance().put(key, redisList);
        }

        return redisList;
    }

    public RedisList getWithoutNull(String key) {
        RedisList redisList = get(key);
        return redisList == null ? new RedisList(new LinkedList<>()) : redisList;
    }

}
