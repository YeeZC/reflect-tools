package me.zyee.reflect.handler.proxy;

/**
 * @author yee
 * @date 2018/7/27
 */
public interface ProxyHandler<P> extends BaseProxyHandler<P> {

    ProxyHandler<P> method(String method);

    ProxyHandler<P> returnObject(Object value);

    ProxyHandler<P> output(String msg);
}
