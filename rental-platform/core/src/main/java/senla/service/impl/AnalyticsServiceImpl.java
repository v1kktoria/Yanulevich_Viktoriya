package senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senla.dao.AnalyticsDao;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Analytics;
import senla.service.AnalyticsService;
import senla.util.TransactionManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsDao analyticsDao;

    @Override
    public Analytics create(Analytics analytics) {
        return TransactionManager.executeInTransaction(() -> {
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
