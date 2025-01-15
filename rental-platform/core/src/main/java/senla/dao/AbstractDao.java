package senla.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import senla.exception.DatabaseException;
import senla.exception.DatabaseExceptionEnum;
import senla.model.Identifiable;
import senla.util.JpaUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Identifiable<ID>, ID extends Serializable> implements ParentDao<T, ID> {
    protected abstract Class<T> getEntityClass();

    @Override
    public T save(T entity) {
        try {
            EntityManager entityManager = JpaUtil.getEntityManager();
            entityManager.persist(entity);
            return entity;
        } catch (PersistenceException e) {
            throw new DatabaseException(DatabaseExceptionEnum.SAVE_FAILED);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try {
            EntityManager entityManager = JpaUtil.getEntityManager();
            T entity = entityManager.find(getEntityClass(), id);
            return Optional.ofNullable(entity);
        } catch (PersistenceException e) {
            throw new DatabaseException(DatabaseExceptionEnum.DATABASE_ERROR);
        }
    }
    @Override
    public List<T> findAll() {
        try {
            EntityManager entityManager = JpaUtil.getEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
            criteriaQuery.select(criteriaQuery.from(getEntityClass()));
            return entityManager.createQuery(criteriaQuery).getResultList();
        } catch (PersistenceException e) {
            throw new DatabaseException(DatabaseExceptionEnum.DATABASE_ERROR, e.getMessage());
        }
    }

    @Override
    public void update(T entity) {
        try {
            EntityManager entityManager = JpaUtil.getEntityManager();
            entityManager.merge(entity);
        } catch (PersistenceException e) {
            throw new DatabaseException(DatabaseExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(T entity) {
        try {
            EntityManager entityManager = JpaUtil.getEntityManager();
            entityManager.remove(entity);
        } catch (PersistenceException e) {
            throw new DatabaseException(DatabaseExceptionEnum.DELETE_FAILED, entity.getId());
        }
    }
}
