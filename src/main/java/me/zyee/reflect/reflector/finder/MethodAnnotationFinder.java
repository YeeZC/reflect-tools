package me.zyee.reflect.reflector.finder;

import me.zyee.reflect.ReflectException;
import me.zyee.reflect.list.DefaultReflectList;
import me.zyee.reflect.list.ReflectList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author yee
 * @date 2018/7/25
 */
public class MethodAnnotationFinder {
    private Method method;

    public MethodAnnotationFinder(Method method) {
        this.method = method;
    }

    public ReflectList<Annotation> atMethod() {
        List<Annotation> list = Arrays.asList(method.getDeclaredAnnotations());
        return new DefaultReflectList<Annotation>(list);
    }

    public ReflectList<Annotation>[] atParams() {
        Annotation[][] annotations = method.getParameterAnnotations();
        if (null != annotations) {
            ReflectList<Annotation>[] array = new ReflectList[annotations.length];
            for (int i = 0; i < annotations.length; i++) {
                array[i] = annotations[i] == null ? null : new DefaultReflectList<Annotation>(Arrays.asList(annotations[i]));
            }
            return array;
        }
        return new ReflectList[0];
    }

    public ReflectList<Annotation> atParam(int index) {
        ReflectList<Annotation>[] lists = atParams();
        if (lists.length < index || index < 0) {
            throw new ReflectException("Cannot find parameter annotation index " + index);
        }
        return lists[index];
    }
}
