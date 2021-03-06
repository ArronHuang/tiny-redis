package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.RequestProcessException;
import com.arronhuang.tiny.redis.storage.RedisString;

import java.math.BigDecimal;

public abstract class AbstractCalculateHandler extends AbstractStringCommandHandler {

    public Long getLongValue(String key) {
        RedisString redisString = get(key);
        if (redisString == null) {
            return null;
        }
        try {
            return Long.valueOf(redisString.getValue());
        } catch (NumberFormatException e) {
            throw new RequestProcessException(ErrorCodeEnum.VALUE_IS_NOT_AN_INTEGER_OR_OUT_OF_RANGE);
        }
    }

    public BigDecimal getBigDecimalValue(String key) {
        RedisString redisString = get(key);
        if (redisString == null) {
            return null;
        }
        try {
            return new BigDecimal(redisString.getValue());
        } catch (NumberFormatException e) {
            throw new RequestProcessException(ErrorCodeEnum.VALUE_IS_NOT_A_VALID_FLOAT);
        }
    }

    public long incrementByLongOffset(String key, long offset) {
        Long oldValue = getLongValue(key);

        if (oldValue == null) {
            oldValue = 0L;
        }

        long newValue = oldValue + offset;
        set(key, newValue);

        return newValue;
    }

    public String incrementByFloatOffset(String key, String offset) {
        BigDecimal oldValue = getBigDecimalValue(key);

        if (oldValue == null) {
            oldValue = BigDecimal.ZERO;
        }

        String newValue = oldValue.add(new BigDecimal(offset)).stripTrailingZeros().toPlainString();
        set(key, newValue);

        return newValue;
    }

}
