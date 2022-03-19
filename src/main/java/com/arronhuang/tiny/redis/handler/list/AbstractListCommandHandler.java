package com.arronhuang.tiny.redis.handler.list;

import com.arronhuang.tiny.redis.handler.CommandHandlerTemplate;
import com.arronhuang.tiny.redis.storage.GlobalMap;
import com.arronhuang.tiny.redis.storage.RedisList;

public abstract class AbstractListCommandHandler extends CommandHandlerTemplate {

    /**
     * 根据 key 获取对应的 RedisList, 根据 createIfNotExists 决定当 RedisList 维护的值为空时, 是否创建 RedisList 对象
     *
     * @param key
     * @param createIfNotExists
     * @return
     */
    public RedisList get(String key, boolean createIfNotExists) {
        RedisList redisList = GlobalMap.getInstance().get(key, RedisList.class);

        if (redisList == null && createIfNotExists) {
            redisList = new RedisList();
            GlobalMap.getInstance().put(key, redisList);
        }

        return redisList;
    }

    /**
     * 根据 key 获取对应的 RedisList 的值， 当 RedisList 为空时, 返回空 RedisList 对象
     *
     * @param key
     * @return
     */
    public RedisList getWithoutNull(String key) {
        RedisList redisList = get(key, false);
        return redisList == null ? new RedisList() : redisList;
    }

}
