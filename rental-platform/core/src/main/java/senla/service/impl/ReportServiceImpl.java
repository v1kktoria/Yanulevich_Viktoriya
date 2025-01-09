package senla.service.impl;

import senla.dao.impl.ReportDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Report;
import senla.service.ReportService;
import senla.util.TransactionManager;

import java.util.List;
import java.util.Optional;

@Component
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDAOImpl reportDAO;

    @Override
    public Optional<Report> create(Report report) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(reportDAO.save(report));
        });
    }

    @Override
    public Optional<Report> getById(Integer id) {
        return TransactionManager.executeInTransaction(() -> {
            return Optional.ofNullable(reportDAO.findById(id));
        });
    }

    @Override
    public List<Report> getAll() {
        return TransactionManager.executeInTransaction(() -> {
            List<Report> reports = reportDAO.findAll();
            reports.forEach(Report::loadLazyFields);
            return reports;
        });
    }

    @Override
    public void updateById(Integer id, Report report) {
        TransactionManager.executeInTransaction(() -> {
            report.setId(id);
            reportDAO.update(report);
            return Optional.empty();
        });
    }

    @Override
    public void deleteById(Integer id) {
        TransactionManager.executeInTransaction(() -> {
            reportDAO.deleteById(id);
            return Optional.empty();
        });
    }
}
