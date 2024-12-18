package senla.service;

import senla.model.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {

    Optional<Application> create(Application application);

    Optional<Application> getById(Integer id);

    Optional<Application> getByPropertyId(Integer id);

    List<Application> getAll();

    void updateById(Integer id, Application application);

    void deleteById(Integer id);
}
