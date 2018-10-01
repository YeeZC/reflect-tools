package me.zyee.reflect.reflector.finder;

/**
 * @author yee
 * @date 2018/7/24
 */
public interface ReflectFinder<T> {
    T withoutArgs();

    T withArgs(Class... args);

    T withAnyArgs();
}
