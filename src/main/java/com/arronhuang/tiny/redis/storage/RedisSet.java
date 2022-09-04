package com.arronhuang.tiny.redis.storage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RedisSet extends RedisObject implements Cloneable, Serializable {

    private Set<String> value = new HashSet<>();

    /**
     * 添加指定元素
     *
     * @param element
     * @return
     */
    public boolean add(String element) {
        return value.add(element);
    }

    /**
     * 获取维护的元素数量
     *
     * @return
     */
    public int size() {
        return value.size();
    }

    /**
     * 移除指定元素
     *
     * @param element
     * @return
     */
    public boolean remove(String element) {
        return value.remove(element);
    }

    /**
     * 移除传入 RedisSet 中包含的所有元素
     *
     * @param redisSet
     */
    public void removeAll(RedisSet redisSet) {
        Set<String> elements = redisSet.values();

        if (CollUtil.isEmpty(elements)) {
            return;
        }

        value.removeAll(elements);
    }

    /**
     * 返回 RedisSet 中维护的所有元素
     */
    public Set<String> values() {
        return value;
    }

    /**
     * 与传入的 RedisSet 取交集
     *
     * @param redisSet
     */
    public void innerJoin(RedisSet redisSet) {
        if (redisSet == null) {
            return;
        }

        Iterator<String> iterator = values().iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            if (!redisSet.contains(element)) {
                iterator.remove();
            }
        }
    }

    /**
     * 与传入的 RedisSet 取并集
     *
     * @param redisSet
     */
    public void outerJoin(RedisSet redisSet) {
        value.addAll(redisSet.values());
    }


    /**
     * 判断传入的元素是否包含在该 RedisSet 中
     *
     * @param element
     * @return
     */
    public boolean contains(String element) {
        return value.contains(element);
    }

    /**
     * 随机从 RedisSet 中 pop 一个对象
     *
     * @return
     */
    public String pop() {
        String randomElement = value.stream().findAny().get();
        value.remove(randomElement);
        return randomElement;
    }

    /**
     * 随机从 RedisSet 中选出至多 qty 个不重复对象返回
     *
     * @return
     */
    public String random() {
        return value.stream().findAny().get();
    }

    /**
     * 返回自身的一个深克隆对象
     *
     * @return
     */
    @Override
    public RedisSet clone() {
        return ObjectUtil.cloneByStream(this);
    }

}
