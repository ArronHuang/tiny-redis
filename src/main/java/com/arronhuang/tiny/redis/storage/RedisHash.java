package com.arronhuang.tiny.redis.storage;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.RequestProcessException;
import lombok.Data;

import java.math.BigDecimal;
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

    public int incrementBy(String fieldName, int offset) {
        String oldHashValue = value.getOrDefault(fieldName, "0");

        try {
            int newHashValue = Integer.valueOf(oldHashValue) + offset;
            value.put(fieldName, String.valueOf(newHashValue));
            return newHashValue;
        } catch (NumberFormatException e) {
            throw new RequestProcessException(ErrorCodeEnum.HASH_VALUE_IS_NOT_AN_INTEGER);
        }
    }

    public String incrementByFloat(String fieldName, String offset) {
        BigDecimal oldValue;
        String oldHashValue = value.getOrDefault(fieldName, "0");

        try {
            oldValue = this.value == null ? BigDecimal.ZERO : new BigDecimal(oldHashValue);
        } catch (NumberFormatException e) {
            throw new RequestProcessException(ErrorCodeEnum.VALUE_IS_NOT_A_VALID_FLOAT);
        }

        String newValue = oldValue.add(new BigDecimal(offset)).stripTrailingZeros().toPlainString();
        value.put(fieldName, newValue);
        return newValue;
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
