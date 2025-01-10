package senla.service;

import senla.model.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    Profile create(Profile profile);

    Profile getById(Integer id);

    List<Profile> getAll();

    void updateById(Integer id, Profile profile);

    void deleteById(Integer id);
}
