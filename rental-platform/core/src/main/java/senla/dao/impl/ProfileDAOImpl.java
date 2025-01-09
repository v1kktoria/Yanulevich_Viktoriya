package senla.dao.impl;

import senla.dao.AbstractDAO;
import senla.dicontainer.annotation.Component;
import senla.model.Profile;

@Component
public class ProfileDAOImpl extends AbstractDAO<Profile, Integer> {

    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }
}
