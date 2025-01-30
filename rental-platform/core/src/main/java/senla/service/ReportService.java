package senla.service;


import senla.dto.ReportDto;

import java.util.List;

public interface ReportService {

    ReportDto create(ReportDto reportDto);

    ReportDto getById(Integer id);

    List<ReportDto> getAll();

    void updateById(Integer id, ReportDto reportDto);

    void deleteById(Integer id);
}
