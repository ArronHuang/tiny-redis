package com.arronhuang.tiny.redis.storage;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.RequestProcessException;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RedisString extends RedisObject<String> {

    private String value;

    public RedisString() {
    }

    /**
     * 对持有的值进行累加操作, 累加的值取决于传入的 offset
     *
     * @param offset 增加量
     * @return 操作之后的数值
     */
    public long increment(long offset) {
        Long oldValue;

        try {
            oldValue = this.value == null ? 0 : Long.valueOf(this.value);
        } catch (NumberFormatException e) {
            throw new RequestProcessException(ErrorCodeEnum.VALUE_IS_NOT_AN_INTEGER_OR_OUT_OF_RANGE);
        }

        long newValue = oldValue + offset;
        this.value = String.valueOf(newValue);
        return newValue;
    }

    /**
     * 对持有的值进行累加操作, 累加的值取决于传入的 offset
     *
     * @param offset 增加量
     * @return 操作之后的数值
     */
    public String incrementByFloat(String offset) {
        BigDecimal oldValue;
        try {
            oldValue = this.value == null ? BigDecimal.ZERO : new BigDecimal(this.value);
        } catch (NumberFormatException e) {
            throw new RequestProcessException(ErrorCodeEnum.VALUE_IS_NOT_A_VALID_FLOAT);
        }

        String newValue = oldValue.add(new BigDecimal(offset)).stripTrailingZeros().toPlainString();
        this.value = newValue;
        return newValue;
    }

}
