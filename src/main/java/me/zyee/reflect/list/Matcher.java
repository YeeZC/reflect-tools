package me.zyee.reflect.list;

/**
 * @author yee
 * @date 2018/7/24
 */
public interface Matcher<T> {
    boolean accept(T t);
}
