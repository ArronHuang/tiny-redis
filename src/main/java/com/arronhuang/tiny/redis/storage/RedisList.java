package com.arronhuang.tiny.redis.storage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Data
@NoArgsConstructor
public class RedisList extends RedisObject {

    private LinkedList<String> value = new LinkedList<>();

    public RedisList(Collection<String> elements) {
        this.value = new LinkedList<>(elements);
    }

    /**
     * 在指定位置插入元素
     *
     * @param index   需要插入的位置
     * @param element 需要插入的元素
     */
    public void add(int index, String element) {
        if (value == null) {
            value = new LinkedList<>();
        }

        value.add(index, element);
    }

    /**
     * 在列表头部插入元素
     *
     * @param elements 被插入的元素
     */
    public int leftPush(List<String> elements) {
        for (String element : elements) {
            value.addFirst(element);
        }
        return size();
    }

    /**
     * 在列表尾部插入元素
     *
     * @param elements 被插入的元素
     */
    public int rightPush(List<String> elements) {
        for (String element : elements) {
            value.addLast(element);
        }
        return size();
    }

    /**
     * 从列表头部移除元素
     *
     * @return 被移除的元素, 如果列表中已经没有元素, 则返回 null
     */
    public String leftPop() {
        String result = null;
        try {
            result = value.removeFirst();
        } catch (NoSuchElementException e) {
            // 如果列表为空, 直接返回空对象
        }
        return result;
    }

    /**
     * 从列表尾部移除元素
     *
     * @return 被移除的元素, 如果列表中已经没有元素, 则返回 null
     */
    public String rightPop() {
        String result = null;
        try {
            result = value.removeLast();
        } catch (NoSuchElementException e) {
            // 如果列表为空, 直接返回空对象
        }
        return result;
    }

    /**
     * 从列表头部开始, 查询指定的元素
     *
     * @param element 需要查找的元素
     * @return 对应元素的下标, 如果没有找到对应的元素, 则返回 -1
     */
    public int findFirstElement(String element) {
        if (CollUtil.isEmpty(value)) {
            return -1;
        }

        for (int i = 0; i < value.size(); i++) {
            if (ObjectUtil.equals(value.get(i), element)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 获取列表的长度
     *
     * @return 列表长度
     */
    public int size() {
        if (CollUtil.isEmpty(value)) {
            return 0;
        }
        return value.size();
    }

}
