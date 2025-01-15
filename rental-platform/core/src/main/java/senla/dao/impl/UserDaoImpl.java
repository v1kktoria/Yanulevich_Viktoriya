package senla.dao.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import senla.dao.AbstractDao;
import senla.dao.UserDao;
import senla.model.User;
import senla.model.User_;
import senla.util.JpaUtil;

import java.util.List;

public class UserDaoImpl extends AbstractDao<User, Integer> implements UserDao {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public boolean existsByUsername(String username) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(root))
                .where(criteriaBuilder.equal(root.get("username"), username));
        Long count = entityManager.createQuery(criteriaQuery).getSingleResult();
        return count > 0;
    }

    @Override
    public List<User> findAllWithEssentialDetails() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityGraph<?> entityGraph = entityManager.getEntityGraph(User_.GRAPH_USER_ROLES_PROPERTIES_APPLICATIONS);
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getResultList();
    }
}
