package senla.dicontainer.util;

import senla.dicontainer.annotation.Value;
import senla.dicontainer.exception.DIException;
import senla.dicontainer.exception.DIExceptionEnum;
import senla.util.PropertiesUtil;

import java.util.Arrays;

public class ValueDependencyInjection {
    public static void inject(Object target) {
        Class<?> clazz = target.getClass();

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Value.class))
                .forEach(field -> {
                    Value valueAnnotation = field.getAnnotation(Value.class);
                    String key = valueAnnotation.value();
                    String propertyValue = PropertiesUtil.get(key);

                    if (propertyValue != null) {
                        try {
                            field.setAccessible(true);
                            Object convertedValue = convertValue(field.getType(), propertyValue);
                            field.set(target, convertedValue);
                        } catch (IllegalAccessException e) {
                            throw new DIException(DIExceptionEnum.FIELD_INJECTION_FAILED);
                        }
                    }
                });
    }

    private static Object convertValue(Class<?> targetType, String value) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(value);
        } else if (targetType == short.class || targetType == Short.class) {
            return Short.parseShort(value);
        } else if (targetType == byte.class || targetType == Byte.class) {
            return Byte.parseByte(value);
        }
        return null;
    }
}

