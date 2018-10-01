package me.zyee.reflect;

import me.zyee.reflect.handler.proxy.BaseProxyHandler;
import me.zyee.reflect.handler.proxy.CustomProxyHandler;
import me.zyee.reflect.handler.proxy.DefaultProxyHandler;
import me.zyee.reflect.handler.proxy.ProxyHandler;
import me.zyee.reflect.reflector.ClassReflector;
import me.zyee.reflect.reflector.FieldReflector;
import me.zyee.reflect.reflector.MethodReflector;
import me.zyee.reflect.reflector.ObjectReflector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author yee
 * @date 2018/7/24
 */
public final class ReflectUtils {
    public static <T> ClassReflector<T> on(Class<T> tClass) {
        return new ClassReflector<T>(tClass);
    }

    public static ClassReflector on(String clazz) {
        try {
            return on(Class.forName(clazz, false, ReflectUtils.class.getClassLoader()));
        } catch (ClassNotFoundException e) {
            throw new ReflectException(e);
        }
    }

    public static FieldReflector on(Field field) {
        return new FieldReflector(field);
    }

    public static MethodReflector on(Method method) {
        return new MethodReflector(method);
    }

    public static ObjectReflector on(Object target) {
        return new ObjectReflector(target);
    }

    public static <P> ProxyHandler<P> proxy(Class<P> proxyClass) {
        return new DefaultProxyHandler<P>(proxyClass);
    }

    public static <P> BaseProxyHandler<P> proxy(Class<P> proxyClass, Class<? extends InvocationHandler> invokeClass, Object... args) {
        return new CustomProxyHandler<P>(proxyClass, invokeClass, args);
    }
}
