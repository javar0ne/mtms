package com.dg.mtms.server;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Singleton<T extends Singleton<T>> {
    private static final Map<Class<?>, Singleton<?>> instances = new HashMap<>();

    protected Singleton() {}

    @SuppressWarnings("unchecked")
    public static <T extends Singleton<T>> T getInstance(Class<?> clazz) {
        synchronized (instances) {
            if (!instances.containsKey(clazz)) {
                throw new IllegalStateException("Singleton not initialized: " + clazz.getName());
            }
            return (T) instances.get(clazz);
        }
    }

    protected static <T extends Singleton<T>> void addInstance(Singleton<T> instance) {
        synchronized (instances) {
            instances.put(instance.getClass(), instance);
        }
    }
}

