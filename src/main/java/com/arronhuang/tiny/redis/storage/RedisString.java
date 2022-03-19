package com.arronhuang.tiny.redis.storage;

import cn.hutool.core.date.DateUtil;
import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.handler.RequestProcessException;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RedisString extends RedisObject {

    private String value;

    public RedisString() {
    }

    public RedisString(Object value) {
        this.value = String.valueOf(value);
    }

    public RedisString(Object value, int ttl) {
        this.value = String.valueOf(value);
        this.expireTime = DateUtil.offsetMillisecond(new Date(), ttl).getTime();
    }

    /**
     * 对持有的值进行 +1 操作
     *
     * @return 操作之后的数值
     */
    public long increment() {
        return increment(1);
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
