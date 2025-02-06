package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import senla.dao.AbstractDao;
import senla.dao.AnalyticsDao;
import senla.model.Analytics;

import java.util.List;

@Repository
public class AnalyticsDaoImpl extends AbstractDao<Analytics, Integer> implements AnalyticsDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Class<Analytics> getEntityClass() {
        return Analytics.class;
    }

    @Override
    public List<Analytics> findByPropertyId(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Analytics> criteriaQuery = criteriaBuilder.createQuery(Analytics.class);
        Root<Analytics> root = criteriaQuery.from(Analytics.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("property").get("id"), id));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
