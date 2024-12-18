package senla.service.impl;

import senla.dao.impl.ReportDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Report;
import senla.service.ReportService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDAOImpl reportDAO;

    @Override
    public Optional<Report> create(Report report) {
        return Optional.ofNullable(reportDAO.create(report));
    }

    @Override
    public Optional<Report> getById(Integer id) {
        return Optional.ofNullable(reportDAO.getByParam(id));
    }

    @Override
    public List<Report> getAll() {
        return reportDAO.getAll();
    }

    @Override
    public void updateById(Integer id, Report report) {
        reportDAO.updateById(id, report);
    }

    @Override
    public void deleteById(Integer id) {
        reportDAO.deleteById(id);
    }
}
