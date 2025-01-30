package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.ApplicationDao;
import senla.dao.PropertyDao;
import senla.dao.UserDao;
import senla.dto.ApplicationDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Application;
import senla.model.Property;
import senla.model.User;
import senla.service.ApplicationService;
import senla.util.mappers.ApplicationMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationDao applicationDao;

    private final PropertyDao propertyDao;

    private final UserDao userDao;

    private final ApplicationMapper applicationMapper;

    @Override
    public ApplicationDto create(ApplicationDto applicationDto) {
        Property property = propertyDao.findById(applicationDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, applicationDto.getPropertyId()));

        User tenant = userDao.findById(applicationDto.getTenantId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, applicationDto.getTenantId()));

        Application application = applicationMapper.toEntity(applicationDto, property, tenant);
        ApplicationDto createdApplication = applicationMapper.toDto(applicationDao.save(application));
        log.info("Заявка успешно создана с ID: {}", createdApplication.getId());
        return createdApplication;
    }

    @Override
    public ApplicationDto getById(Integer id) {
        ApplicationDto application = applicationMapper.toDto(applicationDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Заявка успешно получена: {}", application);
        return application;
    }

    @Override
    public List<ApplicationDto> getByPropertyId(Integer id) {
        List<Application> applications = applicationDao.findByPropertyId(id);
        List<ApplicationDto> applicationDtos = applications.stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} заявок для недвижимости с ID: {}", applicationDtos.size(), id);
        return applicationDtos;
    }

    @Override
    public List<ApplicationDto> getAll() {
        List<Application> applications = applicationDao.findAll();
        List<ApplicationDto> applicationDtos = applications.stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} заявок", applicationDtos.size());
        return applicationDtos;
    }

    @Override
    public void updateById(Integer id, ApplicationDto applicationDto) {
        Application application = applicationDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        applicationDto.setId(id);
        applicationMapper.updateEntity(applicationDto, application);
        applicationDao.update(application);
        log.info("Заявка с ID: {} успешно обновлена", id);
    }

    @Override
    public void deleteById(Integer id) {
        Application application = applicationDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        applicationDao.delete(application);
        log.info("Заявка с ID: {} успешно удалена", id);
    }
}
