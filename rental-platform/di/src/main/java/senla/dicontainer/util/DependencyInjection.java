package senla.dicontainer.util;

import senla.dicontainer.DIContainer;

public interface DependencyInjection {
    void inject(DIContainer diContainer, Class<?> clazz, Object classInstance);
}
