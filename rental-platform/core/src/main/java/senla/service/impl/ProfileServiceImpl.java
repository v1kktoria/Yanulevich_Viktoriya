package senla.service.impl;

import senla.dao.impl.ProfileDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Profile;
import senla.service.ProfileService;
import senla.util.TransactionManager;
import senla.util.validator.ProfileValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDAOImpl profileDAO;

    @Override
    public Optional<Profile> create(Profile profile) {
        return TransactionManager.executeInTransaction(() -> {
            ProfileValidator.validate(profile);
            profile.setRegistrationDate(LocalDateTime.now());
            return Optional.ofNullable(profileDAO.save(profile));
        });
    }

    @Override
    public Optional<Profile> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(profileDAO.findById(id));
        });
    }

    @Override
    public List<Profile> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return profileDAO.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Profile profile) {
        TransactionManager.executeInTransaction(() -> {
            profile.setId(id);
            ProfileValidator.validate(profile);
            profileDAO.update(profile);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            profileDAO.deleteById(id);
            return Optional.empty();
        });
    }
}