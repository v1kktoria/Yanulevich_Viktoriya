package senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senla.dao.ApplicationDao;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Application;
import senla.service.ApplicationService;
import senla.util.TransactionManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationDao applicationDao;

    @Override
    public Application create(Application application) {
        return TransactionManager.executeInTransaction(() -> {
            return applicationDao.save(application);
        });
    }

    @Override
    public Application getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return applicationDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        });
    }

    @Override
    public List<Application> getByPropertyId(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return applicationDao.findByPropertyId(id);
        });
    }

    @Override
    public List<Application> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            return applicationDao.findAll();
        });
    }

    @Override
    public void updateById(Integer id, Application application) {
        TransactionManager.executeInTransaction(() -> {
            application.setId(id);
            applicationDao.update(application);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            Application application = applicationDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            applicationDao.delete(application);
        });
    }
}
