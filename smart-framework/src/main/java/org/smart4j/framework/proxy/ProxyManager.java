package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 */
public class ProxyManager {

    public static  <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method tarfetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, targetObject, tarfetMethod, methodProxy, methodParams, proxyList).doProxyChain();
            }
        });
    }
}
