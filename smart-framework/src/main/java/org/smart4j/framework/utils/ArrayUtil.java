package org.smart4j.framework.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 */
public class ArrayUtil {
    /**
     * 判断数组是否非空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * 判断数组是否为空
     */
    public static boolean isEmpty(Object[] objects) {
        return ArrayUtils.isEmpty(objects);
    }
}
