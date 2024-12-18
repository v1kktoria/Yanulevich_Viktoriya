package senla.service;

import senla.model.Application;

import java.util.List;

public interface ApplicationService {

    Application create(Application application);

    Application getById(Integer id);

    Application getByPropertyId(Integer id);

    List<Application> getAll();

    void updateById(Integer id, Application application);

    void deleteById(Integer id);
}
