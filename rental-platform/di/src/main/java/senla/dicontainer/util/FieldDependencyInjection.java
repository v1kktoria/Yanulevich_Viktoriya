package senla.dicontainer.util;

import senla.dicontainer.DIContainer;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Qualifier;
import senla.dicontainer.exception.DIException;
import senla.dicontainer.exception.DIExceptionEnum;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FieldDependencyInjection implements DependencyInjection{

    public void inject(DIContainer diContainer, Class<?> clazz, Object classInstance) {
        getAutowiredFields(clazz)
                .forEach(field -> {
                    String qualifier = field.isAnnotationPresent(Qualifier.class)
                            ? field.getAnnotation(Qualifier.class).value()
                            : null;
                    Object fieldInstance = diContainer.getBean(field.getType(), field.getName(), qualifier);
                    try {
                        field.set(classInstance, fieldInstance);
                    } catch (IllegalAccessException e) {
                        throw new DIException(DIExceptionEnum.FIELD_INJECTION_FAILED);
                    }
                });
    }

    private Collection<Field> getAutowiredFields(Class<?> clazz) {
        Collection<Field> fields = new ArrayList<>();
        while (clazz != null) {
            List<Field> classFields = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Autowired.class))
                    .peek(field -> field.setAccessible(true))
                    .toList();
            fields.addAll(classFields);
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
