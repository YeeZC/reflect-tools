package me.zyee.reflect.handler.proxy;

import me.zyee.reflect.ReflectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yee
 * @date 2018/7/25
 */
public class DefaultProxyHandler<P> implements ProxyHandler<P> {
    private Class<P> proxyClass;
    private Method currentMethod;
    private Map<Method, ReturnValue.Value> returnObjects = new HashMap<Method, ReturnValue.Value>();

    public DefaultProxyHandler(Class<P> proxyClass) {
        this.proxyClass = proxyClass;
    }

    @Override
    public ProxyHandler<P> method(String method) {
        currentMethod = ReflectUtils.on(proxyClass).reflect().method(method).withAnyArgs();
        return this;
    }

    @Override
    public ProxyHandler<P> returnObject(Object value) {
        returnObjects.put(currentMethod, ReturnValue.value(value));
        return this;
    }

    @Override
    public ProxyHandler<P> output(String msg) {
        returnObjects.put(currentMethod, ReturnValue.out(msg));
        return this;
    }

    @Override
    public P construct() {
        return (P) Proxy.newProxyInstance(proxyClass.getClassLoader(), new Class[]{proxyClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                if (returnObjects.get(method) == null) {
                    if (method.getName().equals("toString")) {
                        return proxyClass.getName();
                    }
                    if (method.getName().equals("equals")) {
                        return proxy == args[0];
                    }
                    return null;
                }
                ReturnValue.Value value = returnObjects.get(method);
                if (value.type == ReturnValue.Type.NORMAL) {
                    return value.value;
                }
                System.out.println(value.value);
                return null;
            }
        });
    }
}
