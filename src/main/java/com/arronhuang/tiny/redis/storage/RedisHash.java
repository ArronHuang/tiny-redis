package com.arronhuang.tiny.redis.storage;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RedisHash extends RedisObject {

    private Map<String, String> value = new HashMap<>();

    /**
     * 根据传入的字段名
     *
     * @param fieldNames
     * @return
     */
    public int removeFields(List<String> fieldNames) {
        return (int) fieldNames.stream()
                .filter(fieldName -> value.remove(fieldName) == null)
                .count();
    }

    public boolean existsField(String fieldName) {
        return value.containsKey(fieldName);
    }

    public String get(String fieldName) {
        return value.get(fieldName);
    }

    public List<String> getAll() {
        List<String> entries = new ArrayList<>();
        value.entrySet()
                .stream()
                .forEach(entry -> {
                    entries.add(entry.getKey());
                    entries.add(entry.getValue());
                });
        return entries;
    }

}
