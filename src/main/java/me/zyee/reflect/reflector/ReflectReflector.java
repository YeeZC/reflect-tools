package me.zyee.reflect.reflector;

import me.zyee.reflect.ReflectException;
import me.zyee.reflect.ReflectUtils;
import me.zyee.reflect.list.Matcher;
import me.zyee.reflect.reflector.finder.ConstructorFinder;
import me.zyee.reflect.reflector.finder.MethodFinder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author yee
 * @date 2018/7/24
 */
public class ReflectReflector {

    private Class type;

    public ReflectReflector(Class type) {
        this.type = type;
    }

    public Field field(String fieldName) {
        try {
            return type.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new ReflectException(e);
        }
    }

    public MethodFinder method(String method) {
        return new MethodFinder(type, method);
    }

    public ConstructorFinder constructor() {
        return new ConstructorFinder(type);
    }

    public <A extends Annotation> A annotation(final Class<A> aClass) {
        List<A> list = ReflectUtils.on(type).reflectAll().annotations().match(new Matcher<A>() {

            @Override
            public boolean accept(A a) {
                return aClass.isAssignableFrom(a.getClass());
            }
        });
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
