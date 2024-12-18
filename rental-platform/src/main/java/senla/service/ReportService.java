package senla.service;


import senla.model.Report;

import java.util.List;

public interface ReportService {

    Report create(Report report);

    Report getById(Integer id);

    List<Report> getAll();

    void updateById(Integer id, Report report);

    void deleteById(Integer id);
}
