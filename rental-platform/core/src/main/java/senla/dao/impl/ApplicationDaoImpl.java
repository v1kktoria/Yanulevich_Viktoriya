package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import senla.dao.AbstractDao;
import senla.dao.ApplicationDao;
import senla.model.Application;
import senla.util.JpaUtil;

import java.util.List;


public class ApplicationDaoImpl extends AbstractDao<Application, Integer> implements ApplicationDao {

    @Override
    protected Class<Application> getEntityClass() {
        return Application.class;
    }

    @Override
    public List<Application> findByPropertyId(Integer id) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> root = criteriaQuery.from(Application.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("property").get("id"), id));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}