package senla.dao.impl;

import senla.dao.AbstractDao;
import senla.dao.ProfileDao;
import senla.model.Profile;

public class ProfileDaoImpl extends AbstractDao<Profile, Integer> implements ProfileDao {

    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }
}
