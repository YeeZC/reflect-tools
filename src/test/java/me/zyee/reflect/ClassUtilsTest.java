package me.zyee.reflect;

import me.zyee.reflect.handler.proxy.ProxyHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author yee
 * @date 2018/7/30
 */
public class ClassUtilsTest {

    @org.junit.Test
    public void isCollection() {
        assertTrue(ClassUtils.isCollection(ArrayList.class));
    }

    @org.junit.Test
    public void isList() {
        assertTrue(ClassUtils.isList(ArrayList.class));
        assertTrue(ClassUtils.isList(new ArrayList<ProxyHandler>().getClass()));
        assertFalse(ClassUtils.isList(HashMap.class));
    }

    @org.junit.Test
    public void isMap() {
        assertTrue(ClassUtils.isMap(ConcurrentHashMap.class));
        assertTrue(ClassUtils.isMap(new HashMap().getClass()));
        assertFalse(ClassUtils.isMap(Set.class));
    }
}