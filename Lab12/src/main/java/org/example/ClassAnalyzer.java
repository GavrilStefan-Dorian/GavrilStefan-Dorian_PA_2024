package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.junit.Test;

public class ClassAnalyzer {

    public static void analyzePath(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File does not exist: " + path);
            return;
        }

        if (file.isDirectory()) {
            analyzeDirectory(file);
        } else if (path.endsWith(".class")) {
            analyzeClassFromFile(file);
        } else if (path.endsWith(".jar")) {
            analyzeJar(file);
        } else {
            System.out.println("Invalid path: " + path);
        }
    }

    private static void analyzeDirectory(File directory) throws IOException, ClassNotFoundException {
        Queue<File> queue = new LinkedList<>();
        queue.add(directory);

        while (!queue.isEmpty()) {
            File current = queue.poll();
            for (File file : Objects.requireNonNull(current.listFiles())) {
                if (file.isDirectory()) {
                    queue.add(file);
                } else if (file.getName().endsWith(".class")) {
                    analyzeClassFromFile(file);
                }
            }
        }
    }

    private static void analyzeJar(File jarFile) throws IOException, ClassNotFoundException {
        try (JarInputStream jarStream = new JarInputStream(new FileInputStream(jarFile))) {
            JarEntry entry;
            while ((entry = jarStream.getNextJarEntry()) != null) {
                if (entry.getName().endsWith(".class")) {
                    analyzeClassFromStream(jarFile, entry.getName());
                }
            }
        }
    }

    private static void analyzeClassFromFile(File file) throws ClassNotFoundException, MalformedURLException {
        String className = getClassNameFromFile(file);
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.getParentFile().toURI().toURL()});
        Class<?> cls = Class.forName(className, true, classLoader);
        analyzeClass(cls);
    }

    private static String getClassNameFromFile(File file) {
        String path = file.getPath();
        String className = path.substring(path.indexOf("org"), path.length() - 6);  // Remove ".class"
        return className.replace(File.separatorChar, '.');
    }

    private static void analyzeClassFromStream(File jarFile, String entryName) throws ClassNotFoundException, MalformedURLException {
        String className = entryName.replace("/", ".").replace(".class", "");
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{jarFile.toURI().toURL()});
        Class<?> cls = Class.forName(className, true, classLoader);
        analyzeClass(cls);
    }

    public static void analyzeClass(String className) throws ClassNotFoundException {
        Class<?> cls = Class.forName(className);
        analyzeClass(cls);
    }

    public static void analyzeClass(Class<?> cls) {
        System.out.println("Class name: " + cls.getName());
        System.out.println("Modifiers: " + Modifier.toString(cls.getModifiers()));
        System.out.println("Super class: " + cls.getSuperclass());

        System.out.println("Interfaces:");
        for (Class<?> iface : cls.getInterfaces()) {
            System.out.println("  " + iface.getName());
        }

        System.out.println("Ctors:");
        for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
            System.out.println("  " + constructor);
        }

        System.out.println("Fields:");
        for (Field field : cls.getDeclaredFields()) {
            System.out.println("  " + field);
        }

        System.out.println("Methods:");
        for (Method method : cls.getDeclaredMethods()) {
            System.out.println("  " + method);
        }

        System.out.println("Annotations:");
        for (Annotation annotation : cls.getAnnotations()) {
            System.out.println("  " + annotation);
        }

        invokeTestMethods(cls);
    }

    private static void invokeTestMethods(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        int invokedTests = 0;
        int totalTests = 0;

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                totalTests++;
                try {
                    if (Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers()) && method.getParameterCount() == 0) {
                        System.out.println("Invoke @Test method: " + method.getName());
                        method.invoke(cls.getDeclaredConstructor().newInstance());
                        invokedTests++;
                    }
                } catch (Exception e) {
                    System.out.println("Error invoking @Test method: " + method.getName());
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Count of @Test methods: " + totalTests + ", ran succesfully: " + invokedTests);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Input a path");
            return;
        }
        try {
            analyzePath(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
