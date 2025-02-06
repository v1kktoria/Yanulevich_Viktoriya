package senla.util.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import senla.dto.ReportDto;
import senla.model.Report;
import senla.model.User;

@Component
@RequiredArgsConstructor
public class ReportMapper {

    private final ModelMapper modelMapper;

    public ReportDto toDto(Report report) {
        ReportDto reportDto = modelMapper.map(report, ReportDto.class);
        reportDto.setUserId(report.getUser() != null ? report.getUser().getId() : null);
        return reportDto;
    }

    public Report toEntity(ReportDto reportDto, User user) {
        Report report = modelMapper.map(reportDto, Report.class);
        report.setUser(user);
        return report;
    }

    public void updateEntity(ReportDto reportDto, Report report) {
        modelMapper.map(reportDto, report);
    }
}
