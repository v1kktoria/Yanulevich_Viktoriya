package senla.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import senla.exception.DatabaseException;
import senla.exception.DatabaseExceptionEnum;
import senla.model.Identifiable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public abstract class AbstractDao<T extends Identifiable<ID>, ID extends Serializable> implements ParentDao<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    protected abstract Class<T> getEntityClass();

    @Override
    public T save(T entity) {
        try {
            entityManager.persist(entity);
            return entity;
        } catch (PersistenceException e) {
            log.error("Ошибка при сохранении сущности: {}", entity, e);
            throw new DatabaseException(DatabaseExceptionEnum.SAVE_FAILED);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try {
            T entity = entityManager.find(getEntityClass(), id);
            return Optional.ofNullable(entity);
        } catch (PersistenceException e) {
            log.error("Ошибка при поиске сущности с ID: {}", id, e);
            throw new DatabaseException(DatabaseExceptionEnum.DATABASE_ERROR);
        }
    }
    @Override
    public List<T> findAll() {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
            criteriaQuery.select(criteriaQuery.from(getEntityClass()));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (PersistenceException e) {
            log.error("Ошибка при получении всех сущностей", e);
            throw new DatabaseException(DatabaseExceptionEnum.DATABASE_ERROR, e.getMessage());
        }
    }

    @Override
    public void update(T entity) {
        try {
            entityManager.merge(entity);
        } catch (PersistenceException e) {
            log.error("Ошибка при обновлении сущности с id {}: {}", entity.getId(), e.getMessage(), e);
            throw new DatabaseException(DatabaseExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(T entity) {
        try {
            entityManager.remove(entity);
        } catch (PersistenceException e) {
            log.error("Ошибка при удалении сущности с id {}: {}", entity.getId(), e.getMessage(), e);
            throw new DatabaseException(DatabaseExceptionEnum.DELETE_FAILED, entity.getId());
        }
    }
}
