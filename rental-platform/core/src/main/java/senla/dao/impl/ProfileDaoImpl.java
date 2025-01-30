package senla.dao.impl;

import org.springframework.stereotype.Repository;
import senla.dao.AbstractDao;
import senla.dao.ProfileDao;
import senla.model.Profile;

@Repository
public class ProfileDaoImpl extends AbstractDao<Profile, Integer> implements ProfileDao {

    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }
}
