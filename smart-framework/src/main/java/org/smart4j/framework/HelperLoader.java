package org.smart4j.framework;

import org.smart4j.framework.helper.*;
import org.smart4j.framework.utils.ClassUtil;

import java.util.Arrays;

/**
 * 加载相应的Helper类
 */
public final class HelperLoader {
    public static void init() {
        //AOPHelper要在IocHelper之前加载，因为首先要通过AOPHelper获取代理对象，然后才能通过IocHelper进行依赖注入
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AOPHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        Arrays.asList(classList).forEach((cls) -> {
            ClassUtil.loadClass(cls.getName());
        });
    }
}
