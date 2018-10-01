package me.zyee.reflect.handler.proxy;

import java.lang.reflect.InvocationHandler;

/**
 * @author yee
 * @date 2018/7/25
 */
public abstract class AbstractInvocationHandler implements InvocationHandler {
    protected Object proxyObject;

    public AbstractInvocationHandler(Object proxyObject) {
        this.proxyObject = proxyObject;
    }

    public Object getProxyObject() {
        return proxyObject;
    }
}
