package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.util.JpaUtil;
import senla.model.Analytics;
import senla.model.Property;

import java.util.List;

@Component
public class AnalyticsDAOImpl extends AbstractDAO<Analytics, Integer> {

    @Override
    protected Class<Analytics> getEntityClass() {
        return Analytics.class;
    }

    public List<Analytics> findByProperty(Property property) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Analytics> criteriaQuery = criteriaBuilder.createQuery(Analytics.class);
        Root<Analytics> root = criteriaQuery.from(Analytics.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("property"), property));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
