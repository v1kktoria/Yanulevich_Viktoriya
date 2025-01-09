package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.model.Address;
import senla.model.Property;
import senla.util.JpaUtil;

import java.util.List;

@Component
public class AddressDAOImpl extends AbstractDAO<Address, Integer> {

    @Override
    protected Class<Address> getEntityClass() {
        return Address.class;
    }

    public List<Address> findByProperty(Property property) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("property"), property));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
