package senla.service.impl;

import senla.dao.impl.AnalyticsDAOImpl;
import senla.dao.impl.PropertyDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Analytics;
import senla.model.Property;
import senla.service.AnalyticsService;
import senla.util.TransactionManager;
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
        return TransactionManager.executeInTransaction(() -> {
            AnalyticsValidator.validate(analytics);
            return Optional.ofNullable(analyticsDAO.save(analytics));
        });
    }

    @Override
    public Optional<Analytics> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(analyticsDAO.findById(id));
        });
    }

    @Override
    public List<Analytics> getByPropertyId(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            Property property = propertyDAO.findById(id);
            return analyticsDAO.findByProperty(property);
        });
    }

    @Override
    public List<Analytics> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return analyticsDAO.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Analytics analytics) {
        TransactionManager.executeInTransaction(() -> {
            analytics.setId(id);
            AnalyticsValidator.validate(analytics);
            analyticsDAO.update(analytics);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            analyticsDAO.deleteById(id);
            return Optional.empty();
        });
    }
}
