package me.zyee.reflect.handler;

import me.zyee.reflect.ReflectException;
import me.zyee.reflect.ReflectUtils;

import java.lang.reflect.Constructor;

/**
 * @author yee
 * @date 2018/7/24
 */
public class ConstructorInvocationHandler<T> implements InvocationHandler<T> {

    private Class<T> type;

    public ConstructorInvocationHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    public T withoutArgs() {
        try {
            Constructor<T> constructor = ReflectUtils.on(type).reflect().constructor().withoutArgs();
            constructor.setAccessible(true);

            return constructor.newInstance();
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    @Override
    public T withArgs(Object... args) {
        Class[] classes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = null != args[i] ? args[i].getClass() : null;
        }

        try {
            Constructor<T> constructor = ReflectUtils.on(type).reflect().constructor().withArgs(classes);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }
}
