package senla.service.impl;

import senla.dao.impl.AnalyticsDAOImpl;
import senla.dao.impl.PropertyDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Analytics;
import senla.model.Property;
import senla.service.AnalyticsService;
import senla.util.validator.AnalyticsValidator;

import java.util.List;
import java.util.Optional;

@Component
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private AnalyticsDAOImpl analyticsDAO;

    @Autowired
    private PropertyDAOImpl propertyDAO;

    @Override
    public Optional<Analytics> create(Analytics analytics) {
        AnalyticsValidator.validate(analytics);
        return Optional.ofNullable(analyticsDAO.create(analytics));
    }

    @Override
    public Optional<Analytics> getById(Integer id) {
        return Optional.ofNullable(analyticsDAO.getByParam(id));
    }

    @Override
    public Optional<Analytics> getByPropertyId(Integer id) {
        Property property = propertyDAO.getByParam(id);
        return Optional.ofNullable(analyticsDAO.getByParam(property));
    }

    @Override
    public List<Analytics> getAll() {
        return analyticsDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Analytics analytics) {
        AnalyticsValidator.validate(analytics);
        analyticsDAO.updateById(id, analytics);
    }

    @Override
    public void deleteById(Integer id) {
        analyticsDAO.deleteById(id);
    }
}
