package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.ApplicationDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Application;
import senla.model.Property;
import senla.model.User;
import senla.model.constant.Status;
import senla.repository.ApplicationRepository;
import senla.repository.PropertyRepository;
import senla.repository.UserRepository;
import senla.service.ApplicationService;
import senla.service.ChatService;
import senla.service.NotificationService;
import senla.util.mappers.ApplicationMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final PropertyRepository propertyRepository;

    private final UserRepository userRepository;

    private final ApplicationMapper applicationMapper;

    private final ChatService chatService;

    private final NotificationService notificationService;

    @Transactional
    @Override
    public ApplicationDto create(ApplicationDto applicationDto) {
        Property property = propertyRepository.findById(applicationDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, applicationDto.getPropertyId()));

        User tenant = userRepository.findById(applicationDto.getTenantId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, applicationDto.getTenantId()));

        User owner = userRepository.findById(applicationDto.getOwnerId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, applicationDto.getOwnerId()));

        if (applicationRepository.existsByTenantIdAndPropertyId(applicationDto.getTenantId(), applicationDto.getPropertyId())) {
            throw new ServiceException(ServiceExceptionEnum.APPLICATION_ALREADY_EXISTS,
                    applicationDto.getTenantId(), applicationDto.getPropertyId());
        }

        Application application = applicationMapper.toEntity(applicationDto, property, tenant);
        application.setCreatedAt(LocalDateTime.now());
        application.setStatus(Status.PENDING);
        ApplicationDto createdApplication = applicationMapper.toDto(applicationRepository.save(application));

        notificationService.sendNewApplicationNotification(owner.getProfile().getEmail(),
                tenant.getUsername(), property.getDescription());

        log.info("Заявка успешно создана с ID: {}", createdApplication.getId());
        return createdApplication;
    }

    @Override
    public ApplicationDto getById(Integer id) {
        ApplicationDto application = applicationMapper.toDto(applicationRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Заявка успешно получена: {}", application);
        return application;
    }

    @Override
    public List<ApplicationDto> getByPropertyId(Integer id) {
        List<Application> applications = applicationRepository.findAllByPropertyId(id);
        List<ApplicationDto> applicationDtos = applications.stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} заявок для недвижимости с ID: {}", applicationDtos.size(), id);
        return applicationDtos;
    }

    @Override
    public List<ApplicationDto> getAll() {
        List<Application> applications = applicationRepository.findAll();
        List<ApplicationDto> applicationDtos = applications.stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} заявок", applicationDtos.size());
        return applicationDtos;
    }

    @Transactional
    public void acceptApplication(Integer applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, applicationId));

        if (application.getStatus() == Status.APPROVED) {
            log.info("Заявка уже принята");
            return;
        }

        application.setStatus(Status.APPROVED);
        applicationRepository.save(application);

        chatService.create(application.getTenant().getId(), application.getOwner().getId());

        notificationService.sendApprovalNotification(application);
    }

    @Transactional
    public void rejectApplication(Integer applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, applicationId));

        if (application.getStatus() == Status.REJECTED) {
            log.info("Заявка уже отклонена");
            return;
        }

        application.setStatus(Status.REJECTED);
        applicationRepository.save(application);

        notificationService.sendRejectionNotification(application);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        applicationRepository.delete(application);
        log.info("Заявка с ID: {} успешно удалена", id);
    }
}
