package senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senla.aop.MeasureExecutionTime;
import senla.dto.ApplicationDto;
import senla.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody @Valid ApplicationDto applicationDto) {
        log.info("Создание новой заявки с данными: {}", applicationDto);
        ApplicationDto application = applicationService.create(applicationDto);
        return ResponseEntity.status(201).body(application);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationDto>> getAllApplications() {
        log.info("Запрос на получение всех заявок");
        List<ApplicationDto> applications = applicationService.getAll();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@applicationSecurityService.hasAccess(authentication, #id)")
    public ResponseEntity<ApplicationDto> getApplication(@PathVariable("id") Integer id) {
        log.info("Запрос на получение заявки с ID: {}", id);
        ApplicationDto application = applicationService.getById(id);
        return ResponseEntity.ok(application);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@applicationSecurityService.hasAccess(authentication, #id)")
    public ResponseEntity<String> deleteApplication(@PathVariable("id") Integer id) {
        log.info("Удаление заявки с ID: {}", id);
        applicationService.deleteById(id);
        return ResponseEntity.ok("Заявка успешно удалена");
    }

    @PutMapping("/{id}/accept")
    @PreAuthorize("@applicationSecurityService.isOwner(authentication, #id)")
    public ResponseEntity<String> acceptApplication(@PathVariable("id") Integer id) {
        log.info("Принятие заявки с ID: {}", id);
        applicationService.acceptApplication(id);
        return ResponseEntity.ok("Заявка принята");
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("@applicationSecurityService.isOwner(authentication, #id)")
    public ResponseEntity<String> rejectApplication(@PathVariable("id") Integer id) {
        log.info("Отклонение заявки с ID: {}", id);
        applicationService.rejectApplication(id);
        return ResponseEntity.ok("Заявка отклонена");
    }
}