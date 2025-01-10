package senla.service.impl;

import senla.dao.ProfileDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Profile;
import senla.service.ProfileService;
import senla.util.TransactionManager;
import senla.util.validator.ProfileValidator;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDao profileDao;

    @Override
    public Profile create(Profile profile) {
        return TransactionManager.executeInTransaction(() -> {
            ProfileValidator.validate(profile);
            profile.setRegistrationDate(LocalDateTime.now());
            return profileDao.save(profile);
        });
    }

    @Override
    public Profile getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return profileDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        });
    }

    @Override
    public List<Profile> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return profileDao.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Profile profile) {
        TransactionManager.executeInTransaction(() -> {
            profile.setId(id);
            ProfileValidator.validate(profile);
            profileDao.update(profile);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            Profile profile = profileDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            profileDao.delete(profile);
        });
    }
}