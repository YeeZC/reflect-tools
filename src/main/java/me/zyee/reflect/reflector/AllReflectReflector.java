package me.zyee.reflect.reflector;

import me.zyee.reflect.list.DefaultReflectList;
import me.zyee.reflect.list.ReflectList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yee
 * @date 2018/7/24
 */
public class AllReflectReflector<T> {

    private Class<T> type;

    public AllReflectReflector(Class<T> type) {
        this.type = type;
    }

    public ReflectList<Field> fields() {
        return new DefaultReflectList<Field>(Arrays.asList(type.getDeclaredFields()));
    }

    public ReflectList<Method> methods() {
        return new DefaultReflectList<Method>(allMethods());
    }

    public ReflectList<Constructor<T>> constructors() {
        return new DefaultReflectList<Constructor<T>>(allConstructors());
    }

    public ReflectList<Annotation> annotations() {
        return new DefaultReflectList<Annotation>(Arrays.asList(type.getDeclaredAnnotations()));
    }


    private List<Constructor<T>> allConstructors() {
        List<Constructor<T>> result = new ArrayList<Constructor<T>>();
        for (Constructor<?> constructor : type.getDeclaredConstructors()) {
            result.add((Constructor<T>) constructor);
        }
        return result;
    }

    private List<Method> allMethods() {
        Class current = type;
        List<Method> methods = new ArrayList<Method>();
        do {
            methods.addAll(Arrays.asList(current.getDeclaredMethods()));
        } while (null != (current = current.getSuperclass()));
        return methods;
    }
}
