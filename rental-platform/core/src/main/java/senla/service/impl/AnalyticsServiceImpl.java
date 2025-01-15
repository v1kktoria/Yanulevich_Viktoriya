package senla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senla.dao.AnalyticsDao;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Analytics;
import senla.service.AnalyticsService;
import senla.util.TransactionManager;
import senla.util.validator.AnalyticsValidator;

import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsDao analyticsDao;

    @Autowired
    public AnalyticsServiceImpl(AnalyticsDao analyticsDao) {
        this.analyticsDao = analyticsDao;
    }

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
            return analyticsDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
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
            Analytics analytics = analyticsDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            analyticsDao.delete(analytics);
        });
    }
}
