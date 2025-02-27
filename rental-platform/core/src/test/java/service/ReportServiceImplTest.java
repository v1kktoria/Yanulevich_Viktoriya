package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senla.dto.ReportDto;
import senla.exception.ServiceException;
import senla.model.Report;
import senla.model.User;
import senla.repository.ReportRepository;
import senla.repository.UserRepository;
import senla.service.impl.ReportServiceImpl;
import senla.util.mappers.ReportMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReportMapper reportMapper;

    @InjectMocks
    private ReportServiceImpl reportService;

    private ReportDto reportDto;

    private Report report;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        reportDto = new ReportDto();
        reportDto.setId(1);
        reportDto.setUserId(1);

        user = new User();
        user.setId(1);

        report = new Report();
        report.setId(1);
        report.setUser(user);
    }

    @Test
    void testCreate() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(reportMapper.toEntity(reportDto, user)).thenReturn(report);
        when(reportRepository.save(report)).thenReturn(report);
        when(reportMapper.toDto(report)).thenReturn(reportDto);

        ReportDto createdReport = reportService.create(reportDto);

        assertNotNull(createdReport);
        assertEquals(1, createdReport.getId());
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    void testCreateUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reportService.create(reportDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetById() {
        when(reportRepository.findById(1)).thenReturn(Optional.of(report));
        when(reportMapper.toDto(report)).thenReturn(reportDto);

        ReportDto result = reportService.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(reportRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reportService.getById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testGetAll() {
        List<Report> reports = List.of(report);
        when(reportRepository.findAll()).thenReturn(reports);
        when(reportMapper.toDto(report)).thenReturn(reportDto);

        List<ReportDto> result = reportService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateById() {
        when(reportRepository.findById(1)).thenReturn(Optional.of(report));

        reportDto.setId(1);
        reportService.updateById(1, reportDto);

        verify(reportMapper, times(1)).updateEntity(reportDto, report);
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    void testUpdateByIdNotFound() {
        when(reportRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reportService.updateById(1, reportDto));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        when(reportRepository.findById(1)).thenReturn(Optional.of(report));

        reportService.deleteById(1);

        verify(reportRepository, times(1)).delete(report);
    }

    @Test
    void testDeleteByIdNotFound() {
        when(reportRepository.findById(1)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> reportService.deleteById(1));

        assertEquals("Объект с ID 1 не найден", exception.getMessage());
    }
}
