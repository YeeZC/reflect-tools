package me.zyee.reflect.handler;

import me.zyee.reflect.ReflectException;

import java.lang.reflect.Field;

/**
 * @author yee
 * @date 2018/7/25
 */
public class FieldGetter {
    private Object object;
    private Field field;

    public FieldGetter(Object object, Field field) {
        this.object = object;
        this.field = field;
    }

    public Object get() {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new ReflectException(e);
        }
    }
}
