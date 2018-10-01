package me.zyee.reflect.reflector;

import me.zyee.reflect.reflector.finder.MethodAnnotationFinder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yee
 * @date 2018/7/24
 */
public class MethodReflector {
    private Method method;

    public MethodReflector(Method method) {
        this.method = method;
    }

    public <A extends Annotation> A annotation(Class<A> aClass) {
        return method.getAnnotation(aClass);
    }

    public MethodAnnotationFinder annotations() {
        return new MethodAnnotationFinder(method);
    }

    public Class returnType() {
        return method.getReturnType();
    }

    public Type[] genericReturnTypes() {
        Type type = method.getGenericReturnType();
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getActualTypeArguments();
        } else {
            return null;
        }
    }

    public Class[] paramType() {
        return method.getParameterTypes();
    }
}
