package senla.dicontainer.util;

import senla.dicontainer.DIContainer;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Qualifier;
import senla.dicontainer.exception.DIException;
import senla.dicontainer.exception.DIExceptionEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class SetterDependencyInjection implements DependencyInjection{

    public void inject(DIContainer diContainer, Class<?> clazz, Object classInstance) {
        Arrays.stream(getAutowiredMethods(clazz))
                .filter(method -> method.getParameters().length == 1)
                .forEach(method -> {
                    Parameter parameter = method.getParameters()[0];
                    String qualifier = parameter.isAnnotationPresent(Qualifier.class)
                            ? parameter.getAnnotation(Qualifier.class).value()
                            : null;
                    Object parameterInstance = diContainer.getBean(parameter.getType(), parameter.getName(), qualifier);
                    try {
                        method.invoke(classInstance, parameterInstance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new DIException(DIExceptionEnum.METHOD_INJECTION_FAILED);
                    }
                });
    }

    private Method[] getAutowiredMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Autowired.class) && method.getName().startsWith("set"))
                .peek(method -> method.setAccessible(true))
                .toArray(Method[]::new);
    }
}
