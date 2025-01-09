package senla.service.impl;

import senla.dao.impl.ApplicationDAOImpl;
import senla.dao.impl.PropertyDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Analytics;
import senla.model.Application;
import senla.model.Property;
import senla.service.ApplicationService;

import java.util.List;
import java.util.Optional;

@Component
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDAOImpl applicationDAO;

    @Autowired
    private PropertyDAOImpl propertyDAO;

    @Override
    public Optional<Application> create(Application application) {
        return Optional.ofNullable(applicationDAO.create(application));
    }

    @Override
    public Optional<Application> getById(Integer id) {
        return Optional.ofNullable(applicationDAO.getByParam(id));
    }

    @Override
    public Optional<Application> getByPropertyId(Integer id) {
        Property property = propertyDAO.getByParam(id);
        return Optional.ofNullable(applicationDAO.getByParam(property));
    }

    @Override
    public List<Application> getAll() {
        return applicationDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Application application) {
        applicationDAO.updateById(id, application);
    }

    @Override
    public void deleteById(Integer id) {
        applicationDAO.deleteById(id);
    }
}
