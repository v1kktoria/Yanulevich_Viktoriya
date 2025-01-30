package senla.dao.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import senla.dao.AbstractDao;
import senla.dao.PropertyDao;
import senla.model.Property_;
import senla.model.Property;

import java.util.List;

@Repository
public class PropertyDaoImpl extends AbstractDao<Property, Integer> implements PropertyDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Class<Property> getEntityClass() {
        return Property.class;
    }

    @Override
    public List<Property> findByUserId(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
        Root<Property> root = criteriaQuery.from(Property.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("owner").get("id"), id));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Property> findAllWithEssentialDetails() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph(Property_.GRAPH_PROPERTY_OWNER_ADDRESS_IMAGES);
        return entityManager.createQuery("SELECT p FROM Property p", Property.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }

}