package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.utils.CollectionUtil;
import org.smart4j.framework.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * 依赖注入助手类
 */
public class IocHelper {
    static {
        //获取所有Bean类与Bean实例的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            //遍历BeanMap
            beanMap.forEach((beanClass, beanInstance) -> {
                //获取Bean类定义的所有成员变量（简称Bean Field）
                Field[] beanFields = beanClass.getDeclaredFields();
                //遍历Bean Field
                Arrays.asList(beanFields).forEach(field -> {
                    //判断当前Field上是否带有Inject注解
                    if (field.isAnnotationPresent(Inject.class)) {
                        //在BeanMap中获取Field对应的实例
                        Class<?> beanFieldClass = field.getType();
                        Object beanFieldInstance = beanMap.get(beanFieldClass);
                        if (beanFieldInstance != null) {
                            //通过反射初始化BeanField的值
                            ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
                        }
                    }
                });

            });
        }
    }
}
