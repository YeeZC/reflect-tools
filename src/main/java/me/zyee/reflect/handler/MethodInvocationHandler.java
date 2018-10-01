package me.zyee.reflect.handler;

import me.zyee.reflect.ReflectException;
import me.zyee.reflect.ReflectUtils;

import java.lang.reflect.Method;

/**
 * @author yee
 * @date 2018/7/24
 */
public class MethodInvocationHandler implements InvocationHandler {
    private Object target;
    private Method method;

    public MethodInvocationHandler(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    @Override
    public Object withoutArgs() {
        return withArgs();
    }

    @Override
    public Object withArgs(Object... args) {
        method.setAccessible(true);
        try {
            return method.invoke(target, args);
        } catch (Exception e) {

            Class[] classes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classes[i] = args[i] == null ? null : args[i].getClass();
            }
            method = ReflectUtils.on(target.getClass()).reflect().method(method.getName()).withArgs(classes);
            try {
                method.setAccessible(true);
                return method.invoke(target, args);
            } catch (Exception e1) {
                throw new ReflectException(e1);
            }
        }
    }
}
