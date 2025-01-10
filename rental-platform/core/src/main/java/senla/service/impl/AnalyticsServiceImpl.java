package senla.service.impl;

import senla.dao.AnalyticsDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Analytics;
import senla.service.AnalyticsService;
import senla.util.TransactionManager;
import senla.util.validator.AnalyticsValidator;

import java.util.List;

@Component
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private AnalyticsDao analyticsDao;

    @Override
    public Analytics create(Analytics analytics) {
        return TransactionManager.executeInTransaction(() -> {
            AnalyticsValidator.validate(analytics);
            return analyticsDao.save(analytics);
        });
    }

    @Override
    public Analytics getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return analyticsDao.findById(id);
        });
    }

    @Override
    public List<Analytics> getByPropertyId(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return analyticsDao.findByPropertyId(id);
        });
    }

    @Override
    public List<Analytics> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return analyticsDao.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Analytics analytics) {
        TransactionManager.executeInTransaction(() -> {
            analytics.setId(id);
            AnalyticsValidator.validate(analytics);
            analyticsDao.update(analytics);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            analyticsDao.deleteById(id);
        });
    }
}
