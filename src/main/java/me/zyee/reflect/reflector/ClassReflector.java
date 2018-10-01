package me.zyee.reflect.reflector;

import me.zyee.reflect.handler.ConstructorInvocationHandler;
import me.zyee.reflect.handler.InvocationHandler;

/**
 * @author yee
 * @date 2018/7/24
 */
public class ClassReflector<T> {
    private Class<T> clazz;

    public ClassReflector(Class<T> tClass) {
        this.clazz = tClass;
    }

    public ReflectReflector reflect() {
        return new ReflectReflector(clazz);
    }

    public AllReflectReflector reflectAll() {
        return new AllReflectReflector(clazz);
    }

    public InvocationHandler<T> construct() {
        return new ConstructorInvocationHandler<T>(clazz);
    }
}
