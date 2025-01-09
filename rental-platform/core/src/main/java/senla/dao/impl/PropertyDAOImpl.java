package senla.dao.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.model.Property_;
import senla.model.User;
import senla.util.JpaUtil;
import senla.model.Property;

import java.util.List;

@Component
public class PropertyDAOImpl extends AbstractDAO<Property, Integer> {

    @Override
    protected Class<Property> getEntityClass() {
        return Property.class;
    }

    public List<Property> findByUser(User user) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
        Root<Property> root = criteriaQuery.from(Property.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("owner"), user));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Property> findAllWithEssentialDetails() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityGraph<?> entityGraph = entityManager.getEntityGraph(Property_.GRAPH_PROPERTY_OWNER_ADDRESS_IMAGES);
        return entityManager.createQuery("SELECT p FROM Property p", Property.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }

}