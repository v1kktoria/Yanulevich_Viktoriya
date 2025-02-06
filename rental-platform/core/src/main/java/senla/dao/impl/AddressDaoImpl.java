package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import senla.dao.AbstractDao;
import senla.dao.AddressDao;
import senla.model.Address;

import java.util.List;

@Repository
public class AddressDaoImpl extends AbstractDao<Address, Integer> implements AddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Class<Address> getEntityClass() {
        return Address.class;
    }

    @Override
    public List<Address> findByPropertyId(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("property").get("id"), id));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
