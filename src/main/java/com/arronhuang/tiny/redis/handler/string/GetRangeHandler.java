package com.arronhuang.tiny.redis.handler.string;

import com.arronhuang.tiny.redis.netty.RespResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetRangeHandler extends AbstractStringCommandHandler {

    @Override
    public RespResponse doHandle(List<String> args) {
        String key = args.get(0);
        int fromPosition = Integer.valueOf(args.get(1));
        int toPosition = Integer.valueOf(args.get(2));

        String value = getValue(key, true);

        fromPosition = translatePosition(value.length(), fromPosition);
        toPosition = translatePosition(value.length(), toPosition);

        int temp = fromPosition;
        fromPosition = Math.min(fromPosition, toPosition);
        toPosition = Math.max(temp, toPosition) + 1;

        return RespResponse.bulkString(value.substring(fromPosition, toPosition));
    }

    @Override
    public void checkArgs(List<String> args) {

    }

    private int translatePosition(int stringLength, int position) {
        if (position >= 0) {
            return Math.min(stringLength - 1, position);
        } else {
            return Math.max(0, stringLength + position);
        }
    }

}
