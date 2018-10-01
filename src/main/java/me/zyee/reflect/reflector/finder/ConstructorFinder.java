package me.zyee.reflect.reflector.finder;

import me.zyee.reflect.ReflectException;
import me.zyee.reflect.ReflectUtils;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author yee
 * @date 2018/7/24
 */
public class ConstructorFinder<T> implements ReflectFinder<Constructor<T>> {

    private Class<T> type;

    public ConstructorFinder(Class<T> type) {
        this.type = type;
    }

    @Override
    public Constructor<T> withoutArgs() {
        try {
            return type.getConstructor();
        } catch (NoSuchMethodException e) {
            try {
                return type.getDeclaredConstructor();
            } catch (NoSuchMethodException e1) {
                return withArgs();
            }
        }
    }

    @Override
    public Constructor<T> withArgs(Class... args) {
        List<Constructor<T>> constructors = ReflectUtils.on(type).reflectAll().constructors().match(new ConstructorTypeMatcher(args));
        if (null == constructors || constructors.isEmpty()) {
            throw new ReflectException("cannot find any constructor for class " + type.getName());
        }
        return constructors.get(0);
    }

    @Override
    public Constructor<T> withAnyArgs() {
        List<Constructor<T>> constructors = ReflectUtils.on(type).reflectAll().constructors();
        if (null == constructors || constructors.isEmpty()) {
            throw new ReflectException("cannot find any constructor for class " + type.getName());
        }
        return constructors.get(0);
    }
}
