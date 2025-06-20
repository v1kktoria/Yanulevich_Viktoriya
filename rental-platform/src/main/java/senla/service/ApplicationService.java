package senla.service;

import senla.dto.ApplicationDto;

import java.util.List;

public interface ApplicationService {

    ApplicationDto create(ApplicationDto applicationDto);

    ApplicationDto getById(Integer id);

    List<ApplicationDto> getByPropertyId(Integer id);

    List<ApplicationDto> getAll();

    void acceptApplication(Integer applicationId);

    void rejectApplication(Integer applicationId);

    void deleteById(Integer id);
}
