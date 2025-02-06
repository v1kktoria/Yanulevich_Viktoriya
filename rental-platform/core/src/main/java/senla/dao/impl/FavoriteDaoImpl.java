package senla.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import senla.dao.AbstractDao;
import senla.dao.FavoriteDao;
import senla.model.Favorite;

import java.util.List;

@Repository
public class FavoriteDaoImpl extends AbstractDao<Favorite, Integer> implements FavoriteDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected Class<Favorite> getEntityClass() {
        return Favorite.class;
    }

    @Override
    public List<Favorite> findByUserId(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Favorite> criteriaQuery = criteriaBuilder.createQuery(Favorite.class);
        Root<Favorite> root = criteriaQuery.from(Favorite.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("user").get("id"), id));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}