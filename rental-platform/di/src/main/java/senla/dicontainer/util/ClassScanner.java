package senla.dicontainer.util;

import senla.dicontainer.annotation.Component;
import senla.dicontainer.exception.DIException;
import senla.dicontainer.exception.DIExceptionEnum;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassScanner {

    public Set<Class<?>> findClassesWithComponentAnnotation(String packageName) {
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Set<Class<?>> classes = new HashSet<>();
        try {
            Enumeration<URL> resources = classLoader.getResources(packagePath);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if ("file".equals(resource.getProtocol())) {
                    String decodedPath = URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8);
                    File directory = new File(decodedPath);
                    if (directory.exists()) {
                        classes.addAll(processDirectory(directory, packageName));
                    }
                } else if ("jar".equals(resource.getProtocol())) {
                    classes.addAll(processJar(resource, packagePath));
                }
            }
        } catch (IOException e) {
            throw new DIException(DIExceptionEnum.FILE_READ_ERROR, packagePath);
        }

        return classes.stream()
                .filter(clazz -> clazz.isAnnotationPresent(Component.class))
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    private Set<Class<?>> processDirectory(File directory, String packageName)  {
        Set<Class<?>> classes = new HashSet<>();
        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(processDirectory(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = new StringBuilder(packageName).append(".").append(file.getName().replace(".class", "")).toString();
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new DIException(DIExceptionEnum.CLASS_NOT_FOUND, className);
                }
            }
        }
        return classes;
    }

    private Set<Class<?>> processJar(URL resource, String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
            JarFile jarFile = jarURLConnection.getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.startsWith(packageName) && entryName.endsWith(".class")) {
                    String className = new StringBuilder(entryName).substring(0, entryName.length() - ".class".length()).replace('/', '.');
                    try {
                        classes.add(Class.forName(className));
                    } catch (ClassNotFoundException e) {
                        throw new DIException(DIExceptionEnum.CLASS_NOT_FOUND, className);
                    }
                }
            }
        } catch (IOException e) {
            throw new DIException(DIExceptionEnum.FILE_READ_ERROR, packageName);
        }
        return classes;
    }
}
