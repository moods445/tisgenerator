package com.modds.generator.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by xiejh on 2017/2/14.
 */
public class JarLoader {

    public static void load(String path) throws Exception {
        load(new File(path));
    }

    public static void load(URL url) throws Exception {
        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(classLoader, url);
    }

    public static void load(File jar) throws Exception {
        load(jar.toURI().toURL());
    }
}
