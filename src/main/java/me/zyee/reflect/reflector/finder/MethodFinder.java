package me.zyee.reflect.reflector.finder;

import me.zyee.reflect.ReflectException;
import me.zyee.reflect.ReflectUtils;
import me.zyee.reflect.list.Matcher;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author yee
 * @date 2018/7/24
 */
public class MethodFinder implements ReflectFinder<Method> {
    private Class type;
    private String methodName;

    public MethodFinder(Class type, String method) {
        this.type = type;
        this.methodName = method;
    }

    @Override
    public Method withoutArgs() {
        return withArgs();
    }

    @Override
    public Method withArgs(Class... args) {
        try {
            return type.getMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            try {
                return type.getDeclaredMethod(methodName, args);
            } catch (NoSuchMethodException e1) {
                throw new ReflectException(e1);
            }
        }
    }

    @Override
    public Method withAnyArgs() {
        List<Method> methods = ReflectUtils.on(type).reflectAll().methods().match(new Matcher<Method>() {
            @Override
            public boolean accept(Method method) {
                return method.getName().equals(methodName);
            }
        });
        if (null == methods || methods.isEmpty()) {
            throw new ReflectException("cannot find any method named: " + methodName);
        }
        return methods.get(0);
    }
}
