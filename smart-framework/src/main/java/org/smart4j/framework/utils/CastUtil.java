package org.smart4j.framework.utils;

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
     * 转型为double型
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * 转型为double型（可指定默认值）
     */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转型为long型
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * 转型为long型（可指定默认值）
     */
    public static long castLong(Object obj, long defaultVaule) {
        long longValue = defaultVaule;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultVaule;
                }
            }
        }
        return longValue;
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
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 转型为boolean型
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 转型为boolean型（可指定默认值）
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                booleanValue = Boolean.parseBoolean(strValue);
            }
        }
        return booleanValue;
    }
}
