package me.zyee.reflect;

/**
 * @author yee
 * @date 2018/10/1
 */
public class TestBean implements TestInterface {

    private String name = "defaultName";
    private int age = 10;

    @Override
    public void print() {
        System.out.println("test print");
    }

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
