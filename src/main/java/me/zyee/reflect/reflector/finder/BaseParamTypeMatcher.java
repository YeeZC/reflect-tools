package me.zyee.reflect.reflector.finder;

import me.zyee.reflect.list.Matcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yee
 * @date 2018/7/24
 */
public abstract class BaseParamTypeMatcher<T> implements Matcher<T> {
    private static Map<Class, Class> mapped = new HashMap<Class, Class>();

    static {
        mapped.put(Integer.TYPE, Integer.class);
        mapped.put(Byte.TYPE, Byte.class);
        mapped.put(Short.TYPE, Short.class);
        mapped.put(Long.TYPE, Long.class);
        mapped.put(Float.TYPE, Float.class);
        mapped.put(Double.TYPE, Double.class);
        mapped.put(Boolean.TYPE, Boolean.class);
        mapped.put(Character.TYPE, Character.class);
    }

    protected Class[] classes;

    public BaseParamTypeMatcher(Class[] classes) {
        this.classes = classes;
    }

    protected boolean match(Class[] src, Class[] dest) {
        if (null != src && dest != null) {
            if (src.length != dest.length) {
                return false;
            }
            for (int i = 0; i < src.length; i++) {
                Class s = src[i];
                Class d = dest[i];
                if (s == null || d == null) {
                    continue;
                }
                if (s.isPrimitive()) {
                    if (d.isPrimitive()) {
                        if (!s.isAssignableFrom(d)) {
                            return false;
                        }
                    } else {
                        if (!mapped.get(s).isAssignableFrom(d)) {
                            return false;
                        }
                    }
                } else {
                    if (d.isPrimitive()) {
                        if (!s.isAssignableFrom(mapped.get(d))) {
                            return false;
                        }
                    } else {
                        if (!s.isAssignableFrom(d)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
