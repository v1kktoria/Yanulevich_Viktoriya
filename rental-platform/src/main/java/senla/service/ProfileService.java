package senla.service;

import senla.model.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    Optional<Profile> create(Profile profile);

    Optional<Profile> getById(Integer id);

    List<Profile> getAll();

    void updateById(Integer id, Profile profile);

    void deleteById(Integer id);
}
