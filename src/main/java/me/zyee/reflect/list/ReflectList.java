package me.zyee.reflect.list;

import java.util.List;

/**
 * @author yee
 * @date 2018/7/24
 */
public interface ReflectList<T> extends List<T> {
    ReflectList<T> match(Matcher<T> matcher);
}
