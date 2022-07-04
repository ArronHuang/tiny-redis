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
     * 根据传入的字段名, 移除对应的 k-v 对
     *
     * @param fieldNames
     * @return
     */
    public int removeFields(List<String> fieldNames) {
        return (int) fieldNames.stream()
                .filter(fieldName -> value.remove(fieldName) == null)
                .count();
    }

    /**
     * 判断传入的字段名在 hash 中是否存在
     *
     * @param fieldName
     * @return
     */
    public boolean existsField(String fieldName) {
        return value.containsKey(fieldName);
    }

    /**
     * 根据字段名获取对应的值
     *
     * @param fieldName
     * @return
     */
    public String get(String fieldName) {
        return value.get(fieldName);
    }

    /**
     * 根据传入的字段名, 为其值添加具体的整型数值
     *
     * @param fieldName
     * @param offset
     * @return
     */
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

    /**
     * 根据传入的字段名, 为其值添加具体的浮点型数值
     *
     * @param fieldName
     * @param offset
     * @return
     */
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

    /**
     * 获取所有的 k-v 值, 以 k1, v1, k2, v2 的形式返回 list
     *
     * @return
     */
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

    /**
     * 返回所有的字段名
     *
     * @return
     */
    public List<String> keys() {
        return new ArrayList<>(value.keySet());
    }

    /**
     * 返回 hash 的容量
     *
     * @return
     */
    public int length() {
        return value.size();
    }

}
