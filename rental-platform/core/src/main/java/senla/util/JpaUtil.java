package senla.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JpaUtil {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("rental-platform-unit");;
    private static final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    public static EntityManager getEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
            entityManagerThreadLocal.set(entityManager);
        }
        return entityManager;
    }

    public static void closeEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            entityManagerThreadLocal.remove();
        }
    }
}
