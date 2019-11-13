package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.utils.ArrayUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 */
public class ControllerHelper {
    /**
     * 用于存放请求与处理器的映射关系（简称Action Map）
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        //遍历这些Controller
        controllerClassSet.forEach((controllerClass) -> {
            //获取Controller中定义的方法
            Method[] methods = controllerClass.getDeclaredMethods();
            if (ArrayUtil.isNotEmpty(methods)) {
                //遍历这些Controller类中定义的方法
                Arrays.asList(methods).forEach((method -> {
                    //判断当前方法是否带有Action注解
                    if (method.isAnnotationPresent(Action.class)) {
                        Action action = method.getAnnotation(Action.class);
                        String mapping = action.value();
                        //验证URL映射规则
                        if (mapping.matches("\\w+:/\\w*")) {
                            String[] arrary = mapping.split(":");
                            //获取请求方法与请求路径
                            String requestMethod = arrary[0];
                            String requestPath = arrary[1];
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(controllerClass, method);
                            //初始化Action Map
                            ACTION_MAP.put(request, handler);
                        }
                    }
                }));
            }
        });
    }
    /**
     *获取 Handler
     * */

}
