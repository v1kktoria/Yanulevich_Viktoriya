package senla.service.impl;

import senla.dao.impl.ProfileDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Profile;
import senla.service.ProfileService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDAOImpl profileDAO;

    @Override
    public Profile create(Profile profile) {
        validate(profile);
        profile.setRegistrationDate(LocalDateTime.now());
        return profileDAO.create(profile);
    }

    @Override
    public Profile getById(Integer id) {
        return profileDAO.getByParam(id);
    }

    @Override
    public List<Profile> getAll() {
        return profileDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Profile profile) {
        validate(profile);
        profileDAO.updateById(id, profile);
    }

    @Override
    public void deleteById(Integer id) {
        profileDAO.deleteById(id);
    }

    private void validate(Profile profile) {
        if (profile.getFirstname() == null || profile.getFirstname().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Имя не может быть пустым");
        }
        if (profile.getLastname() == null || profile.getLastname().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Фамилия не может быть пустой");
        }
        if (!profile.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Некорректный email");
        }
        if (profile.getPhone().isEmpty()) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Телефон не может быть пустым");
        }
    }
}
