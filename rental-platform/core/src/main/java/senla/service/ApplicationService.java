package senla.service;

import senla.model.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {

    Application create(Application application);

    Application getById(Integer id);

    List<Application> getByPropertyId(Integer id);

    List<Application> getAll();

    void updateById(Integer id, Application application);

    void deleteById(Integer id);
}
