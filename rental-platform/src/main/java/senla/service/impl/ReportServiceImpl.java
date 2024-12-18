package senla.service.impl;

import senla.dao.impl.ReportDAOImpl;
import senla.dicontainer.annotation.Autowired;
import senla.dicontainer.annotation.Component;
import senla.model.Report;
import senla.service.ReportService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDAOImpl reportDAO;

    @Override
    public Report create(Report report) {
        return reportDAO.create(report);
    }

    @Override
    public Report getById(Integer id) {
        return reportDAO.getByParam(id);
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
