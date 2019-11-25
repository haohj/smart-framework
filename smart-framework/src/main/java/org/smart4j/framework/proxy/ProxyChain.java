package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 */
public class ProxyChain {
    //目标类
    private final Class<?> targetCalss;
    //目标对象
    private final Object targetObject;
    //目标方法
    private final Method targetMrthod;
    //代理方法
    private final MethodProxy methodProxy;
    //方法参数
    private final Object[] methodParams;
    //代理列表
    private List<Proxy> proxyList = new ArrayList<>();
    //代理索引
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetCalss, Object targetObject, Method targetMrthod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetCalss = targetCalss;
        this.targetObject = targetObject;
        this.targetMrthod = targetMrthod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetCalss() {
        return targetCalss;
    }

    public Method getTargetMrthod() {
        return targetMrthod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return methodResult;
    }
}
