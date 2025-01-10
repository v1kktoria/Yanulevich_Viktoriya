package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import senla.dao.AbstractDao;
import senla.dao.FavoriteDao;
import senla.dicontainer.annotation.Component;
import senla.util.JpaUtil;
import senla.model.Favorite;
import senla.model.User;

import java.util.List;

@Component
public class FavoriteDaoImpl extends AbstractDao<Favorite, Integer> implements FavoriteDao {

    @Override
    protected Class<Favorite> getEntityClass() {
        return Favorite.class;
    }

    @Override
    public List<Favorite> findByUserId(Integer id) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Favorite> criteriaQuery = criteriaBuilder.createQuery(Favorite.class);
        Root<Favorite> root = criteriaQuery.from(Favorite.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("user").get("id"), id));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}