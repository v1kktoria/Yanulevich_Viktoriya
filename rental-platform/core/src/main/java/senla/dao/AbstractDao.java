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
    public T findById(ID id) {
        try {
            EntityManager entityManager = JpaUtil.getEntityManager();
            T entity = entityManager.find(getEntityClass(), id);
            if (entity == null) {
                throw new DatabaseException(DatabaseExceptionEnum.ENTITY_NOT_FOUND, id);
            }
            return entity;
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
    public void deleteById(ID id) {
        try {
            EntityManager entityManager = JpaUtil.getEntityManager();
            T entity = entityManager.find(getEntityClass(), id);
            if (entity == null) {
                throw new DatabaseException(DatabaseExceptionEnum.ENTITY_NOT_FOUND, id);
            }
            entityManager.remove(entity);
        } catch (PersistenceException e) {
            throw new DatabaseException(DatabaseExceptionEnum.DELETE_FAILED, id);
        }
    }
}
