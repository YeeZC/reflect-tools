package me.zyee.reflect;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author yee
 * @date 2018/10/1
 */
public class ReflectUtilsTest {

    @Test
    public void onClass() {
        TestBean bean = ReflectUtils.on(TestBean.class).construct().withoutArgs();
        bean.print();
        assertEquals(10, bean.add(1, 9));
    }

    @Test
    public void onClassName() {
        TestBean bean = (TestBean) ReflectUtils.on("me.zyee.reflect.TestBean").construct().withoutArgs();
        bean.print();
        assertEquals(10, bean.add(1, 9));
    }

    @Test
    public void onField() {
        Field name = ReflectUtils.on("me.zyee.reflect.TestBean").reflect().field("name");
        Field age = ReflectUtils.on("me.zyee.reflect.TestBean").reflect().field("age");
        assertTrue(ClassUtils.isAssignable(ReflectUtils.on(name).fieldType(), String.class));
        assertTrue(ClassUtils.isAssignable(ReflectUtils.on(age).fieldType(), int.class));
    }

    @Test
    public void onMethod() {
        Method add = ReflectUtils.on("me.zyee.reflect.TestBean").reflect().method("add").withAnyArgs();
        assertTrue(ClassUtils.isAssignable(ReflectUtils.on(add).returnType(), int.class));
        Class[] classes = ReflectUtils.on(add).paramType();
        assertTrue(ClassUtils.isAssignable(classes[0], int.class));
        assertTrue(ClassUtils.isAssignable(classes[1], int.class));
    }

    @Test
    public void onObject() {
        TestBean bean = ReflectUtils.on(TestBean.class).construct().withoutArgs();
        assertEquals(ReflectUtils.on(bean).get("name").get(), "defaultName");
        assertEquals(ReflectUtils.on(bean).get("age").get(), 10);
        ReflectUtils.on(bean).set("age").value(100);
        assertEquals(ReflectUtils.on(bean).get("age").get(), 100);
        ReflectUtils.on(bean).set("name").value("tomcat");
        assertEquals(ReflectUtils.on(bean).get("name").get(), "tomcat");
        assertEquals(ReflectUtils.on(bean).invoke("toString").withoutArgs(), "TestBean{name='tomcat', age=100}");
    }
}