package senla.service.impl;

import senla.dao.impl.ProfileDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Profile;
import senla.service.ProfileService;
import senla.util.validator.ProfileValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDAOImpl profileDAO;

    @Override
    public Optional<Profile> create(Profile profile) {
        ProfileValidator.validate(profile);
        profile.setRegistrationDate(LocalDateTime.now());
        return Optional.ofNullable(profileDAO.create(profile));
    }

    @Override
    public Optional<Profile> getById(Integer id) {
        return Optional.ofNullable(profileDAO.getByParam(id));
    }

    @Override
    public List<Profile> getAll() {
        return profileDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Profile profile) {
        ProfileValidator.validate(profile);
        profileDAO.updateById(id, profile);
    }

    @Override
    public void deleteById(Integer id) {
        profileDAO.deleteById(id);
    }
}
