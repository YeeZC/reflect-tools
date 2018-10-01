package me.zyee.reflect.handler;

/**
 * @author yee
 * @date 2018/7/24
 */
public interface InvocationHandler<T> {
    T withoutArgs();

    T withArgs(Object... args);
}
