package me.zyee.reflect.reflector.finder;

import java.lang.reflect.Method;

/**
 * @author yee
 * @date 2018/7/24
 */
public class MethodTypeMatcher extends BaseParamTypeMatcher<Method> {
    public MethodTypeMatcher(Class[] classes) {
        super(classes);
    }

    @Override
    public boolean accept(Method method) {
        return match(method.getParameterTypes(), classes);
    }
}
