package senla.dao.impl;

import senla.dao.AbstractDao;
import senla.dao.ProfileDao;
import senla.dicontainer.annotation.Component;
import senla.model.Profile;

@Component
public class ProfileDaoImpl extends AbstractDao<Profile, Integer> implements ProfileDao {

    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }
}
