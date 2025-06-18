package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.ReportDto;
import senla.exception.ServiceException;
import senla.model.constant.ReportType;
import senla.model.constant.Status;
import senla.service.ReportService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class ReportServiceIntegrationTest {

    @Autowired
    private ReportService reportService;

    private ReportDto reportDto;

    @BeforeEach
    public void setUp() {
        reportDto = new ReportDto();
        reportDto.setUserId(1);
        reportDto.setContentId(1);
        reportDto.setType(ReportType.ADVERTISEMENT);
        reportDto.setStatus(Status.PENDING);
    }

    @Test
    public void testCreateReport() {
        ReportDto createdReport = reportService.create(reportDto);
        assertThat(createdReport).isNotNull();
        assertThat(createdReport.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetReportById() {
        ReportDto createdReport = reportService.create(reportDto);
        ReportDto fetchedReport = reportService.getById(createdReport.getId());
        assertThat(fetchedReport).isNotNull();
        assertThat(fetchedReport.getId()).isEqualTo(createdReport.getId());
    }

    @Test
    public void testGetReportByIdNotFound() {
        assertThrows(ServiceException.class, () -> reportService.getById(9999));
    }

    @Test
    public void testGetAllReports() {
        reportService.create(reportDto);
        List<ReportDto> allReports = reportService.getAll();
        assertThat(allReports).isNotEmpty();
    }

    @Test
    public void testUpdateReport() {
        ReportDto createdReport = reportService.create(reportDto);
        createdReport.setStatus(Status.APPROVED);
        reportService.updateById(createdReport.getId(), createdReport);

        ReportDto updatedReport = reportService.getById(createdReport.getId());
        assertThat(updatedReport.getStatus()).isEqualTo(Status.APPROVED);
    }

    @Test
    public void testDeleteReport() {
        ReportDto createdReport = reportService.create(reportDto);
        reportService.deleteById(createdReport.getId());

        assertThrows(ServiceException.class, () -> reportService.getById(createdReport.getId()));
    }
}