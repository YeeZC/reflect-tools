package me.zyee.reflect.reflector;

import me.zyee.reflect.ReflectException;
import me.zyee.reflect.ReflectUtils;
import me.zyee.reflect.handler.FieldGetter;
import me.zyee.reflect.handler.FieldSetter;
import me.zyee.reflect.handler.InvocationHandler;
import me.zyee.reflect.handler.MethodInvocationHandler;
import me.zyee.reflect.handler.proxy.AbstractInvocationHandler;
import me.zyee.reflect.handler.proxy.CustomProxyHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yee
 * @date 2018/7/24
 */
public class ObjectReflector {
    private Object target;

    public ObjectReflector(Object target) {
        this.target = target;
    }

    public FieldSetter set(Field field) {
        return new FieldSetter(target, field);
    }

    public FieldSetter set(String fieldName) {
        Field field = ReflectUtils.on(target.getClass()).reflect().field(fieldName);
        return set(field);
    }

    public FieldGetter get(Field field) {
        return new FieldGetter(target, field);
    }

    public FieldGetter get(String fieldName) {
        Field field = ReflectUtils.on(target.getClass()).reflect().field(fieldName);
        return get(field);
    }

    public InvocationHandler invoke(Method method) {
        return new MethodInvocationHandler(target, method);
    }

    public InvocationHandler invoke(String methodName) {
        Method method = ReflectUtils.on(target.getClass()).reflect().method(methodName).withAnyArgs();
        return invoke(method);
    }

    public <P> P proxy(Class<P> proxyType, Class<? extends AbstractInvocationHandler> handlerType) {
        if (proxyType.isAssignableFrom(target.getClass())) {
            try {
                return new CustomProxyHandler<P>(proxyType, handlerType, target).construct();
            } catch (ReflectException e) {
                return new CustomProxyHandler<P>(proxyType, handlerType).construct();
            }
        }
        throw new ReflectException(target.getClass().getName() + " is not a sub class of " + proxyType.getName());
    }
}
