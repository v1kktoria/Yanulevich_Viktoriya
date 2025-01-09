package senla.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class TransactionManager {
    public <T> T executeInTransaction(Supplier<T> action) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            T result = action.get();
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
}
