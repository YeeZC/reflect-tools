package me.zyee.reflect.reflector.finder;

import java.lang.reflect.Constructor;

/**
 * @author yee
 * @date 2018/7/24
 */
public class ConstructorTypeMatcher<T> extends BaseParamTypeMatcher<Constructor<T>> {


    public ConstructorTypeMatcher(Class[] classes) {
        super(classes);
    }

    @Override
    public boolean accept(Constructor<T> constructor) {
        Class[] params = constructor.getParameterTypes();
        return match(params, classes);
    }
}
