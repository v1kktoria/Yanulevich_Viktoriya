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

import java.util.List;

@Component
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private AnalyticsDAOImpl analyticsDAO;

    @Autowired
    private PropertyDAOImpl propertyDAO;

    @Override
    public Analytics create(Analytics analytics) {
        validate(analytics);
        return analyticsDAO.create(analytics);
    }

    @Override
    public Analytics getById(Integer id) {
        return analyticsDAO.getByParam(id);
    }

    @Override
    public Analytics getByPropertyId(Integer id) {
        Property property = propertyDAO.getByParam(id);
        return analyticsDAO.getByParam(property);
    }

    @Override
    public List<Analytics> getAll() {
        return analyticsDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Analytics analytics) {
        validate(analytics);
        analyticsDAO.updateById(id, analytics);
    }

    @Override
    public void deleteById(Integer id) {
        analyticsDAO.deleteById(id);
    }

    private void validate(Analytics analytics) {
        if (analytics.getViews() < 0) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Количество просмотров не может быть меньше нуля");
        } else if (analytics.getApplicationsCount() < 0) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Количество заявок не может быть меньше нуля");
        }
    }
}
