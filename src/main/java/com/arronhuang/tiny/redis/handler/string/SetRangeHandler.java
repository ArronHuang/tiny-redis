package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.enums.ErrorCodeEnum;
import com.arronhuang.tiny.redis.netty.RespResponse;
import com.arronhuang.tiny.redis.util.AssertUtil;

import java.util.List;

public class SetRangeHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        int index = Integer.valueOf(args.get(1));
        String contentToInsert = args.get(2);

        String value = getString(key, true);

        int valueLength = value.length();
        if (valueLength < index + 1) {
            while (value.length() != index) {
                value += '\u0000';
            }
            value += contentToInsert;
        } else {
            String newValue = value.substring(0, index);
            newValue += contentToInsert;
            if (contentToInsert.length() < value.length() - index) {
                newValue += value.substring(index + contentToInsert.length());
            }

            value = newValue;
        }

        set(key, value, NO_TTL, false);
        return RespResponse.number(value.length());
    }

    @Override
    public void checkArgs(List<String> args) {
        AssertUtil.isInteger(ErrorCodeEnum.VALUE_IS_NOT_AN_INTEGER_OR_OUT_OF_RANGE, args.get(1));
    }

}
