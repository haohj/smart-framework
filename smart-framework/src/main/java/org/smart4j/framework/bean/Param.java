package org.smart4j.framework.bean;

import org.smart4j.framework.utils.CastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求参数对象
 */
public class Param {
    private Map<String, Object> paramMap = new HashMap<>();

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取long型参数值
     *
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     *
     * @return
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
