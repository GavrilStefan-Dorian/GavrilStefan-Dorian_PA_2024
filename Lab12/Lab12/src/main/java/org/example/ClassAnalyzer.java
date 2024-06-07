package org.example;

import org.objectweb.asm.*;
import org.objectweb.asm.util.TraceClassVisitor;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
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
            instrumentClass(path);
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
                    instrumentClass(file.getPath());
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
                    // Extract class file and instrument it before analysis
                    File tempFile = File.createTempFile("temp", ".class");
                    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = jarStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                    instrumentClass(tempFile.getPath());
                    analyzeClassFromStream(jarFile, entry.getName());
                    tempFile.deleteOnExit();
                }
            }
        }
    }

    public static void analyzeClassFromFile(File file) throws ClassNotFoundException, MalformedURLException {
        String className = getClassNameFromFile(file);
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.getParentFile().toURI().toURL()});
        Class<?> cls = Class.forName(className, true, classLoader);
        analyzeClass(cls);

        try {
            byte[] classBytes = Files.readAllBytes(file.toPath());
            ClassReader cr = new ClassReader(classBytes);
            cr.accept(new TraceClassVisitor(new PrintWriter(System.out)), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        if (method.getParameterCount() == 0) {
                            method.invoke(cls.getDeclaredConstructor().newInstance());
                        } else {
                            Object[] args = new Object[method.getParameterCount()];
                            int count = 0;
                            for (Class<?> type : method.getParameterTypes()) {
                                if (type.getSimpleName().equals("int")) {
                                    args[count++] = random.nextInt(50);
                                } else if (type.getSimpleName().equals("String")) {
                                    args[count++] = "a string";
                                }
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

    public static void instrumentClass(String classFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(classFilePath);
        ClassReader cr = new ClassReader(fis);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    @Override
                    public void visitCode() {
                        super.visitCode();
                        // Insert bytecode instructions here
                        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                        mv.visitLdcInsn("Executing method: " + name);
                        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                    }
                };
            }
        };

        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        fis.close();

        byte[] bytecode = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream(classFilePath);
        fos.write(bytecode);
        fos.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String path = console.readLine();

        if (path.endsWith(".java")) {
            compileJavaFile(path);
            path = path.replace(".java", ".class");
            instrumentClass(path);
        }

        analyzePath(path);
//        generateDynamicTestClass();
    }

    public static void compileJavaFile(String filePath) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Collections.singletonList(filePath));

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);

        boolean success = task.call();

        fileManager.close();

        if (success) {
            System.out.println("Compilare cu succes: " + filePath);
        } else {
            System.out.println("Compilare eșuată: " + filePath);
        }
    }

    public static void generateDynamicTestClass() throws IOException {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, "DynamicTestClass", null, "java/lang/Object", null);

        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();

        MethodVisitor testMethod = cw.visitMethod(Opcodes.ACC_PUBLIC, "testMethod", "()V", null, null);
        testMethod.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        testMethod.visitLdcInsn("Dynamic Test Method");
        testMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        testMethod.visitInsn(Opcodes.RETURN);
        testMethod.visitMaxs(2, 1);
        testMethod.visitEnd();

        cw.visitEnd();

        FileOutputStream fos = new FileOutputStream("DynamicTestClass.class");
        fos.write(cw.toByteArray());
        fos.close();
    }
}
