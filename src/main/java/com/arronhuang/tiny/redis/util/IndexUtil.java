package com.arronhuang.tiny.redis.util;

public final class IndexUtil {

    private IndexUtil() {
    }

    /**
     * 针对 redis 语法中, -1 这种下标进行转换
     * eg: String str = "Hello World";      -1 则为字符 d 所在的位置, 即 index 应该为 10
     *
     * @param index  下标, 允许为负数
     * @param length 元素长度(字符串长度或集合长度)
     * @return
     */
    // fixme 当 index 超过 length 时, 需要返回空集合给客户端
    public static int translateIndex(int index, int length) {
        AssertUtil.isPositiveInteger(length);

        if (index >= length) {
            // 1. index > length
            return length - 1;
        } else if (index >= 0 && index < length) {
            // 2. 0 <= index < length
            return index;
        } else {
            // 3. index < 0
            index = index + length;
            return Math.max(0, index);
        }
    }

}
