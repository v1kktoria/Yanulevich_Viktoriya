package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.util.JpaUtil;
import senla.model.Favorite;
import senla.model.User;

import java.util.List;

@Component
public class FavoriteDAOImpl extends AbstractDAO<Favorite, Integer> {

    @Override
    protected Class<Favorite> getEntityClass() {
        return Favorite.class;
    }

    public List<Favorite> findByUser(User user) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Favorite> criteriaQuery = criteriaBuilder.createQuery(Favorite.class);
        Root<Favorite> root = criteriaQuery.from(Favorite.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("user"), user));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}