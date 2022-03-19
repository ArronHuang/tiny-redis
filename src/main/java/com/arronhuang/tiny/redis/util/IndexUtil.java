package com.arronhuang.tiny.redis.util;

public final class IndexUtil {

    private IndexUtil() {
    }

    /**
     * 针对 redis 语法中, -1 这种下标进行转换
     * eg: String str = "Hello World";      -1 则为字符 d 所在的位置, 即 index 应该为 10
     * length = 11, 传入的 index = -1, 则实际的 index 为 11 + (-1) = 10
     * length = 11, 传入的 index = -12, 则实际的 index 为 max(0, 11 + (-12)) = 0
     *
     * @param index  下标, 允许为负数
     * @param length 元素长度(字符串长度或集合长度)
     * @return
     */
    public static int translateIndex(int index, int length) {
        AssertUtil.isPositiveInteger(length);

        if (index >= length) {
            return length;
        } else if (index >= 0 && index < length) {
            return index;
        } else {
            index = index + length;
            return Math.max(0, index);
        }
    }

}
