package senla.dicontainer;

import senla.dicontainer.exception.DIException;
import senla.dicontainer.exception.DIExceptionEnum;
import senla.dicontainer.util.ClassScanner;
import senla.dicontainer.util.ConstructorDependencyInjection;
import senla.dicontainer.util.FieldDependencyInjection;
import senla.dicontainer.util.SetterDependencyInjection;
import senla.dicontainer.util.ValueDependencyInjection;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DIContainer {
    private final Map<Class<?>, Class<?>> diMap;
    private final Map<Class<?>, Object> applicationScope;

    private final ClassScanner classScanner;

    private static DIContainer diContainer;

    private DIContainer() {
        diMap = new HashMap<>();
        applicationScope = new HashMap<>();
        classScanner = new ClassScanner();
    }

    public static void startApplication(Class<?> mainClass) {
        synchronized (DIContainer.class) {
            if (diContainer == null) {
                diContainer = new DIContainer();
                diContainer.scanAndRegister(mainClass);
            }
        }
    }

    public void scanAndRegister(Class<?> mainClass)  {
        Set<Class<?>> classes = classScanner.findClassesWithComponentAnnotation(mainClass.getPackage().getName());
        register(classes);
    }

    private <T> void register(Collection<Class<?>> classes) {
        classes.forEach(clazz -> {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    if (interfaces.length == 0) {
                        diMap.put(clazz, clazz);
                    } else {
                        Arrays.stream(interfaces)
                                .forEach(iface -> diMap.put(clazz, iface));
                    }
                });

        classes.stream()
                .filter(clazz -> !applicationScope.containsKey(clazz))
                .forEach(clazz -> {
                    Object instance = ConstructorDependencyInjection.inject(this, clazz);
                    injectDependencies(clazz, instance);
                    applicationScope.put(clazz, instance);
                });
    }


    public <T> Object getBean(Class<T> interfaceClass, String fieldName, String qualifier) {
        Class<?> implementationCLass = getImplementationClass(interfaceClass, fieldName, qualifier);
        if (applicationScope.containsKey(implementationCLass)) {
            return applicationScope.get(implementationCLass);
        }

        Object instance = ConstructorDependencyInjection.inject(this, implementationCLass);
        injectDependencies(implementationCLass, instance);
        applicationScope.put(implementationCLass, instance);
        return instance;
    }

    private Class<?> getImplementationClass(Class<?> interfaceClass, String fieldName, String qualifier) {
        Set<Map.Entry<Class<?>, Class<?>>> implementationClasses = diMap.entrySet().stream()
                .filter(entry -> entry.getValue() == interfaceClass)
                .collect(Collectors.toSet());

        if (implementationClasses.isEmpty()) {
            throw new DIException(DIExceptionEnum.NO_IMPLEMENTATION_FOUND, interfaceClass.getName());
        } else if (implementationClasses.size() == 1) return implementationClasses.iterator().next().getKey();

        else {
            String findBy;
            if (qualifier == null || qualifier.trim().isEmpty()) findBy = fieldName;
            else findBy = qualifier;
            return implementationClasses.stream()
                    .filter(entry -> entry.getKey().getSimpleName().equalsIgnoreCase(findBy))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElseThrow(() -> new DIException(DIExceptionEnum.MULTIPLE_IMPLEMENTATIONS_FOUND, interfaceClass.getName()));
        }
    }

    private void injectDependencies(Class<?> clazz, Object classInstance) {
        ValueDependencyInjection.inject(classInstance);
        new FieldDependencyInjection().inject(this, clazz, classInstance);
        new SetterDependencyInjection().inject(this, clazz, classInstance);
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) diContainer.getBean(clazz, null, null);
    }

    public static <T> T getBean(Class<T> clazz, String qualifier) {
        return (T) diContainer.getBean(clazz, null, qualifier);
    }
}
