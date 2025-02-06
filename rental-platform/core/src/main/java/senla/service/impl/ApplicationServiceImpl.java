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
import senla.repository.ApplicationRepository;
import senla.repository.PropertyRepository;
import senla.repository.UserRepository;
import senla.service.ApplicationService;
import senla.util.mappers.ApplicationMapper;

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

    @Transactional
    @Override
    public ApplicationDto create(ApplicationDto applicationDto) {
        Property property = propertyRepository.findById(applicationDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, applicationDto.getPropertyId()));

        User tenant = userRepository.findById(applicationDto.getTenantId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, applicationDto.getTenantId()));

        Application application = applicationMapper.toEntity(applicationDto, property, tenant);
        ApplicationDto createdApplication = applicationMapper.toDto(applicationRepository.save(application));
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
    @Override
    public void updateById(Integer id, ApplicationDto applicationDto) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        applicationDto.setId(id);
        applicationMapper.updateEntity(applicationDto, application);
        applicationRepository.save(application);
        log.info("Заявка с ID: {} успешно обновлена", id);
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
