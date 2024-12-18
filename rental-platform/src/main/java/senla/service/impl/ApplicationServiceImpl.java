package senla.service.impl;

import senla.dao.impl.ApplicationDAOImpl;
import senla.dao.impl.PropertyDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Application;
import senla.model.Property;
import senla.service.ApplicationService;

import java.util.List;

@Component
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDAOImpl applicationDAO;

    @Autowired
    private PropertyDAOImpl propertyDAO;

    @Override
    public Application create(Application application) {
        return applicationDAO.create(application);
    }

    @Override
    public Application getById(Integer id) {
        return applicationDAO.getByParam(id);
    }

    @Override
    public Application getByPropertyId(Integer id) {
        Property property = propertyDAO.getByParam(id);
        return applicationDAO.getByParam(property);
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
