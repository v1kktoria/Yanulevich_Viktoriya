package senla.dicontainer.util;

import senla.dicontainer.annotation.Component;
import senla.dicontainer.exception.DIException;
import senla.dicontainer.exception.DIExceptionEnum;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class ClassScanner {

    public Set<Class<?>> findClassesWithComponentAnnotation(String packageName) {
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageURL = classLoader.getResource(packagePath);
        if (packageURL == null) return Set.of();
        Set<Class<?>> classes = new HashSet<>();
        String decodedPath = URLDecoder.decode(packageURL.getFile(), StandardCharsets.UTF_8);
        File directory = new File(decodedPath);
        if (directory.exists()) processDirectory(directory, packageName, classes);

        return classes.stream()
                .filter(clazz -> clazz.isAnnotationPresent(Component.class))
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    private void processDirectory(File directory, String packageName, Set<Class<?>> classes)  {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                processDirectory(file, packageName + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new DIException(DIExceptionEnum.CLASS_NOT_FOUND, className);
                }
            }
        }
    }
}
