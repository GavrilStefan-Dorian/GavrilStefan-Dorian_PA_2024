package org.example;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

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
        System.out.println("Numele Clasei: " + cls.getName());
        System.out.println("Modificatori: " + Modifier.toString(cls.getModifiers()));
        System.out.println("Superclasa: " + cls.getSuperclass());

        System.out.println("Interfete:");
        for (Class<?> iface : cls.getInterfaces()) {
            System.out.println("  " + iface.getName());
        }

        System.out.println("Constructoare:");
        for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
            System.out.println("  " + constructor);
        }

        System.out.println("Campuri:");
        for (Field field : cls.getDeclaredFields()) {
            System.out.println("  " + field);
        }

        System.out.println("Metode:");
        for (Method method : cls.getDeclaredMethods()) {
            System.out.println("  " + method);
        }

        System.out.println("Annotari:");
        for (Annotation annotation : cls.getAnnotations()) {
            System.out.println("  " + annotation);
        }

        invokeTestMethods(cls);
    }

    private static void invokeTestMethods(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        System.out.println(Arrays.toString(methods));
        int invokedTests = 0;
        int totalTests = 0;
        Random random = new Random();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                totalTests++;
                try {
                    if (Modifier.isPublic(method.getModifiers())) {
                            System.out.println("Invocand metoda @Test cu " + method.getParameterCount() + " parametrii: " + method.getName());
                        if(method.getParameterCount() == 0)
                            method.invoke(cls.getDeclaredConstructor().newInstance());
                        else {
                            Object[] args = new Object[method.getParameterCount()];
                            int count = 0;
                            for(Class<?> type : method.getParameterTypes()){
//                                System.out.println(type.getSimpleName());
                                if(type.getSimpleName().equals("int"))
                                    args[count++] = random.nextInt(50);
                                if(type.getSimpleName().equals("String"))
                                    args[count++] = "a string";
                            }
                            method.invoke(cls.getDeclaredConstructor().newInstance(), args);
                        }
                        invokedTests++;
                    }
                } catch (Exception e) {
                    System.out.println("Exceptie la invocarea metodei @Test: " + method.getName());
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Total metode @Test: " + totalTests + ", Invocate cu succes: " + invokedTests);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String path = console.readLine();
            analyzePath(path);
    }
}
