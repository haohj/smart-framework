package org.smart4j.framework.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal<T> {
    private Map<Thread, T> container = Collections.synchronizedMap(new HashMap<>());

    public void set(T value) {
        container.put(Thread.currentThread(), value);
    }

    public T get() {
        Thread thread = Thread.currentThread();
        T value = container.get(thread);
        //如果当前线程不存在，则将当前线程加入集合中并设置对应value为默认值
        if (value == null && !container.containsKey(thread)) {
            value = initValue();
            container.put(thread, value);
        }
        return value;
    }

    public void remove() {
        container.remove(Thread.currentThread());
    }

    protected T initValue() {
        return null;
    }
}
