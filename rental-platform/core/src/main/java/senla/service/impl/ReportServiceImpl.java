package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.ReportDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Report;
import senla.model.User;
import senla.repository.ReportRepository;
import senla.repository.UserRepository;
import senla.service.ReportService;
import senla.util.mappers.ReportMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final UserRepository userRepository;

    private final ReportMapper reportMapper;

    @Transactional
    @Override
    public ReportDto create(ReportDto reportDto) {
        User user = userRepository.findById(reportDto.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, reportDto.getUserId()));

        Report report = reportMapper.toEntity(reportDto, user);
        Report savedReport = reportRepository.save(report);
        log.info("Жалоба с ID: {} успешно создана", savedReport.getId());
        return reportMapper.toDto(savedReport);
    }

    @Override
    public ReportDto getById(Integer id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        log.info("Жалоба с ID: {} успешно получена", id);
        return reportMapper.toDto(report);
    }

    @Override
    public List<ReportDto> getAll() {
        List<Report> reports = reportRepository.findAll();
        reports.forEach(Report::loadLazyFields);
        log.info("Найдено {} жалоб", reports.size());
        return reports.stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateById(Integer id, ReportDto reportDto) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        reportDto.setId(id);
        reportMapper.updateEntity(reportDto, report);
        reportRepository.save(report);
        log.info("Жалоба с ID: {} успешно обновлена", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        reportRepository.delete(report);
        log.info("Жалоба с ID: {} успешно удалена", id);
    }
}
