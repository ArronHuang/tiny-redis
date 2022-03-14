package com.arronhuang.tiny.redis.storage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Data
public class RedisList extends RedisObject {

    private List<String> value;

    private RedisList(Collection<String> elements) {
        this.value = new LinkedList<>(elements);
    }

    public void add(int index, String element) {
        if (value == null) {
            value = new LinkedList<>();
        }

        value.add(index, element);
    }

    public int findFirstElement(String element) {
        if (CollUtil.isEmpty(value)) {
            return -1;
        }

        for (int i = 0; i < value.size(); i++) {
            if (StrUtil.equals(value.get(i), element)) {
                return i;
            }
        }

        return -1;
    }

    public int size() {
        if (CollUtil.isEmpty(value)) {
            return 0;
        }
        return value.size();
    }

}
