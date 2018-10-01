package me.zyee.reflect;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author yee
 * @date 2018/7/24
 */
public class ClassUtils {
    private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap();
    private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap;

    static {
        primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        primitiveWrapperMap.put(Character.TYPE, Character.class);
        primitiveWrapperMap.put(Short.TYPE, Short.class);
        primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperMap.put(Long.TYPE, Long.class);
        primitiveWrapperMap.put(Double.TYPE, Double.class);
        primitiveWrapperMap.put(Float.TYPE, Float.class);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);

        wrapperPrimitiveMap = new HashMap();
        Iterator var0 = primitiveWrapperMap.keySet().iterator();

        while (var0.hasNext()) {
            Class<?> primitiveClass = (Class) var0.next();
            Class<?> wrapperClass = primitiveWrapperMap.get(primitiveClass);
            if (!primitiveClass.equals(wrapperClass)) {
                wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
            }
        }
    }

    /**
     * 扫描包路径下所有的class文件
     *
     * @param pkg
     * @return
     */
    public static Set<Class<?>> getClassOfPackage(String pkg) {
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();

        String pkgDirName = pkg.replace('.', '/');
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(pkgDirName);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findClassesByFile(pkg, filePath, classes);
                } else if ("jar".equals(protocol)) {
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    findClassesByJar(pkg, jar, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }


    /**
     * 扫描包路径下的所有class文件
     *
     * @param pkgName 包名
     * @param pkgPath 包对应的绝对地址
     * @param classes 保存包路径下class的集合
     */
    private static void findClassesByFile(String pkgName, String pkgPath, Set<Class<?>> classes) {
        File dir = new File(pkgPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }


        // 过滤获取目录，or class文件
        File[] dirfiles = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() || pathname.getName().endsWith("class");
            }
        });


        if (dirfiles == null || dirfiles.length == 0) {
            return;
        }


        String className;
        Class clz;
        for (File f : dirfiles) {
            if (f.isDirectory()) {
                findClassesByFile(pkgName + "." + f.getName(),
                        pkgPath + "/" + f.getName(),
                        classes);
                continue;
            }


            // 获取类名，干掉 ".class" 后缀
            className = f.getName();
            className = className.substring(0, className.length() - 6);

            // 加载类
            clz = loadClass(pkgName + "." + className);
            if (clz != null) {
                classes.add(clz);
            }
        }
    }


    /**
     * 扫描包路径下的所有class文件
     *
     * @param pkgName 包名
     * @param jar     jar文件
     * @param classes 保存包路径下class的集合
     */
    private static void findClassesByJar(String pkgName, JarFile jar, Set<Class<?>> classes) {
        String pkgDir = pkgName.replace(".", "/");


        Enumeration<JarEntry> entry = jar.entries();

        JarEntry jarEntry;
        String name, className;
        Class<?> claze;
        while (entry.hasMoreElements()) {
            jarEntry = entry.nextElement();

            name = jarEntry.getName();
            if (name.charAt(0) == '/') {
                name = name.substring(1);
            }


            if (jarEntry.isDirectory() || !name.startsWith(pkgDir) || !name.endsWith(".class")) {
                // 非指定包路径， 非class文件
                continue;
            }


            // 去掉后面的".class", 将路径转为package格式
            className = name.substring(0, name.length() - 6);
            claze = loadClass(className.replace("/", "."));
            if (claze != null) {
                classes.add(claze);
            }
        }
    }


    public static Class<?> loadClass(String fullClzName) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClzName);
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public static boolean isSimpleType(Class tClass) {
        return isPrimitiveOrWrapper(tClass) || isAssignable(String.class, tClass);
    }

    public static boolean isCollection(Class tClass) {
        return isAssignable(tClass, Collection.class);
    }

    public static boolean isList(Class tClass) {
        return isAssignable(tClass, List.class);
    }

    public static boolean isMap(Class tClass) {
        return isAssignable(tClass, Map.class);
    }

    public static boolean isSet(Class tClass) {
        return isAssignable(tClass, Set.class);
    }

    public static boolean isAssignable(Class<?> cls, Class<?> toClass) {
        return isAssignable(cls, toClass, true);
    }

    public static boolean isAssignable(Class<?> cls, Class<?> toClass, boolean autoboxing) {
        if (toClass == null) {
            return false;
        } else if (cls == null) {
            return !toClass.isPrimitive();
        } else {
            if (autoboxing) {
                if (cls.isPrimitive() && !toClass.isPrimitive()) {
                    cls = primitiveToWrapper(cls);
                    if (cls == null) {
                        return false;
                    }
                }

                if (toClass.isPrimitive() && !cls.isPrimitive()) {
                    cls = wrapperToPrimitive(cls);
                    if (cls == null) {
                        return false;
                    }
                }
            }

            if (cls.equals(toClass)) {
                return true;
            } else if (cls.isPrimitive()) {
                if (!toClass.isPrimitive()) {
                    return false;
                } else if (Integer.TYPE.equals(cls)) {
                    return Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
                } else if (Long.TYPE.equals(cls)) {
                    return Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
                } else if (Boolean.TYPE.equals(cls)) {
                    return false;
                } else if (Double.TYPE.equals(cls)) {
                    return false;
                } else if (Float.TYPE.equals(cls)) {
                    return Double.TYPE.equals(toClass);
                } else if (Character.TYPE.equals(cls)) {
                    return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
                } else if (Short.TYPE.equals(cls)) {
                    return Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
                } else if (!Byte.TYPE.equals(cls)) {
                    return false;
                } else {
                    return Short.TYPE.equals(toClass) || Integer.TYPE.equals(toClass) || Long.TYPE.equals(toClass) || Float.TYPE.equals(toClass) || Double.TYPE.equals(toClass);
                }
            } else {
                return toClass.isAssignableFrom(cls);
            }
        }
    }

    public static Class<?> wrapperToPrimitive(Class<?> cls) {
        return wrapperPrimitiveMap.get(cls);
    }

    public static Class<?> primitiveToWrapper(Class<?> cls) {
        Class<?> convertedClass = cls;
        if (cls != null && cls.isPrimitive()) {
            convertedClass = primitiveWrapperMap.get(cls);
        }

        return convertedClass;
    }

    public static boolean isPrimitiveWrapper(Class<?> type) {
        return wrapperPrimitiveMap.containsKey(type);
    }

    public static boolean isPrimitiveOrWrapper(Class<?> type) {
        if (type == null) {
            return false;
        } else {
            return type.isPrimitive() || isPrimitiveWrapper(type);
        }
    }
}
