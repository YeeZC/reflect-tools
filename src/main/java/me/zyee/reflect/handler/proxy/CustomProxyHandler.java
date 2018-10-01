package me.zyee.reflect.handler.proxy;


import me.zyee.reflect.ReflectUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author yee
 * @date 2018/7/25
 */
public class CustomProxyHandler<P> implements BaseProxyHandler<P> {
    private Class<P> proxyClass;
    private InvocationHandler invocation;

    public CustomProxyHandler(Class<P> proxyClass, Class<? extends InvocationHandler> invokeClass, Object... args) {
        this.proxyClass = proxyClass;
        this.invocation = ReflectUtils.on(invokeClass).construct().withArgs(args);
    }

    @Override
    public P construct() {
        return (P) Proxy.newProxyInstance(proxyClass.getClassLoader(), new Class[]{proxyClass}, invocation);
    }
}
