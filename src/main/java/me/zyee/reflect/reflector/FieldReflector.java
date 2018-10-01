package me.zyee.reflect.reflector;

import me.zyee.reflect.list.DefaultReflectList;
import me.zyee.reflect.list.ReflectList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author yee
 * @date 2018/7/24
 */
public class FieldReflector {
    private Field field;

    public FieldReflector(Field field) {
        this.field = field;
    }

    public <A extends Annotation> A annotation(Class<A> aClass) {
        return field.getAnnotation(aClass);
    }

    public ReflectList<Annotation> annotations() {
        return new DefaultReflectList<Annotation>(Arrays.asList(field.getDeclaredAnnotations()));
    }

    public Type[] genericTypes() {
        Type type = field.getGenericType();
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments();
        }
        return null;
    }

    public Class fieldType() {
        return field.getType();
    }
}
