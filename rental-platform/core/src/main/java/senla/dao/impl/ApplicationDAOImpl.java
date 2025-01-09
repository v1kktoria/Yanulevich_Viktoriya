package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.model.Application;
import senla.model.Property;
import senla.util.JpaUtil;

import java.util.List;


@Component
public class ApplicationDAOImpl extends AbstractDAO<Application, Integer> {

    @Override
    protected Class<Application> getEntityClass() {
        return Application.class;
    }

    public List<Application> findByProperty(Property property) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> root = criteriaQuery.from(Application.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("property"), property));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}