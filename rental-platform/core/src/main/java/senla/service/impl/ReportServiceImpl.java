package senla.service.impl;

import senla.dao.ReportDao;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Report;
import senla.service.ReportService;
import senla.util.TransactionManager;

import java.util.List;

@Component
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public Report create(Report report) {
        return TransactionManager.executeInTransaction(() -> {
            return reportDao.save(report);
        });
    }

    @Override
    public Report getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return reportDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        });
    }

    @Override
    public List<Report> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            List<Report> reports = reportDao.findAll();
            reports.forEach(Report::loadLazyFields);
            return reports;
        });
    }

    @Override
    public void updateById(Integer id, Report report) {
        TransactionManager.executeInTransaction(() -> {
            report.setId(id);
            reportDao.update(report);
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            Report report = reportDao.findById(id)
                    .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
            reportDao.delete(report);
        });
    }
}
