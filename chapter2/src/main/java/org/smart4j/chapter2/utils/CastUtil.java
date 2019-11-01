package org.smart4j.chapter2.utils;

/**
 * 转型操作工具类
 */
public class CastUtil {
    /**
     * 转型为String
     */
    public static String castString(Object obj) {
        return castString(obj, "");
    }

    /**
     * 转型为String（可指定默认值）
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转型为int
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 转型为int（可指定默认值）
     */
    public static int castInt(Object obj, int defaultValue) {
        int value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
        }
        return defaultValue;
    }
}
