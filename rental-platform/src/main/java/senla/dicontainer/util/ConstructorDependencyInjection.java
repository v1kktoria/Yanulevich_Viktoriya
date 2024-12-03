package senla.dicontainer.util;

import senla.dicontainer.DIContainer;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Qualifier;
import senla.dicontainer.exception.DIException;
import senla.dicontainer.exception.DIExceptionEnum;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ConstructorDependencyInjection{

    public static Object inject(DIContainer diContainer, Class<?> clazz) {
        Constructor<?> autowiredConstructor = findAutowiredConstructor(clazz);
        if (autowiredConstructor != null) {
            Object[] parameters = resolveConstructorParameters(diContainer, autowiredConstructor);
            try {
                return autowiredConstructor.newInstance(parameters);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new DIException(DIExceptionEnum.CONSTRUCTOR_INJECTION_FAILED);
            }
        }

        Constructor<?> defaultConstructor;
        try {
            defaultConstructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new DIException(DIExceptionEnum.DEFAULT_CONSTRUCTOR_NOT_FOUND, clazz.getName());
        }
        defaultConstructor.setAccessible(true);
        try {
            return defaultConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DIException(DIExceptionEnum.CONSTRUCTOR_INJECTION_FAILED);
        }
    }

    private static Constructor<?> findAutowiredConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(Autowired.class))
                .peek(constructor -> constructor.setAccessible(true))
                .findFirst()
                .orElse(null);
    }

    private static Object[] resolveConstructorParameters(DIContainer diContainer, Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameters())
                .map(parameter -> {
                    String qualifier = parameter.isAnnotationPresent(Qualifier.class)
                            ? parameter.getAnnotation(Qualifier.class).value()
                            : null;
                    return diContainer.getBean(parameter.getType(), parameter.getName(), qualifier);
                })
                .toArray();
    }
}
