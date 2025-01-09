package senla.service.impl;

import senla.dao.impl.ApplicationDAOImpl;
import senla.dao.impl.PropertyDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Application;
import senla.model.Property;
import senla.service.ApplicationService;
import senla.util.TransactionManager;

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
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(applicationDAO.save(application));
        });
    }

    @Override
    public Optional<Application> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(applicationDAO.findById(id));
        });
    }

    @Override
    public List<Application> getByPropertyId(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            Property property = propertyDAO.findById(id);
            return applicationDAO.findByProperty(property);
        });
    }

    @Override
    public List<Application> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return applicationDAO.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Application application) {
        TransactionManager.executeInTransaction(() -> {
            application.setId(id);
            applicationDAO.update(application);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            applicationDAO.deleteById(id);
            return Optional.empty();
        });
    }
}
