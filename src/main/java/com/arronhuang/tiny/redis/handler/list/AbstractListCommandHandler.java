package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.handler.AbstractCommandHandlerTemplate;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisList;

public abstract class AbstractListCommandHandler extends AbstractCommandHandlerTemplate {

    public RedisList get(String key) {
        return GlobalMap.getInstance().getValueByKeyAndType(key, RedisList.class);
    }

}
