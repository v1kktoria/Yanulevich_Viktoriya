package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import senla.dao.AbstractDao;
import senla.dao.AnalyticsDao;
import senla.util.JpaUtil;
import senla.model.Analytics;

import java.util.List;

public class AnalyticsDaoImpl extends AbstractDao<Analytics, Integer> implements AnalyticsDao {

    @Override
    protected Class<Analytics> getEntityClass() {
        return Analytics.class;
    }

    @Override
    public List<Analytics> findByPropertyId(Integer id) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Analytics> criteriaQuery = criteriaBuilder.createQuery(Analytics.class);
        Root<Analytics> root = criteriaQuery.from(Analytics.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("property").get("id"), id));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
