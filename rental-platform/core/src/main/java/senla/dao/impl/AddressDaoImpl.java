package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import senla.dao.AbstractDao;
import senla.dao.AddressDao;
import senla.dicontainer.annotation.Component;
import senla.model.Address;
import senla.util.JpaUtil;

import java.util.List;

@Component
public class AddressDaoImpl extends AbstractDao<Address, Integer> implements AddressDao {

    @Override
    protected Class<Address> getEntityClass() {
        return Address.class;
    }

    @Override
    public List<Address> findByPropertyId(Integer id) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("property").get("id"), id));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
