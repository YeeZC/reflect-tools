package me.zyee.reflect.handler;

import me.zyee.reflect.ReflectException;

import java.lang.reflect.Field;

/**
 * @author yee
 * @date 2018/7/25
 */
public class FieldSetter {
    private Object object;
    private Field field;

    public FieldSetter(Object object, Field field) {
        this.object = object;
        this.field = field;
    }

    public void value(Object o) {
        field.setAccessible(true);
        try {
            field.set(object, o);
        } catch (IllegalAccessException e) {
            throw new ReflectException(e);
        }
    }
}
