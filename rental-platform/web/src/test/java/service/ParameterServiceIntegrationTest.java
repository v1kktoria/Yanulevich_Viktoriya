package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.ParameterDto;
import senla.exception.ServiceException;
import senla.service.ParameterService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class ParameterServiceIntegrationTest {

    @Autowired
    private ParameterService parameterService;

    private ParameterDto parameterDto;

    @BeforeEach
    public void setUp() {
        parameterDto = new ParameterDto();
        parameterDto.setName("Test Parameter");
    }

    @Test
    public void testCreateParameter() {
        ParameterDto createdParameter = parameterService.create(parameterDto);
        assertThat(createdParameter).isNotNull();
        assertThat(createdParameter.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetParameterById() {
        ParameterDto createdParameter = parameterService.create(parameterDto);
        ParameterDto fetchedParameter = parameterService.getById(createdParameter.getId());
        assertThat(fetchedParameter).isNotNull();
        assertThat(fetchedParameter.getId()).isEqualTo(createdParameter.getId());
    }

    @Test
    public void testGetParameterByIdNotFound() {
        assertThrows(ServiceException.class, () -> parameterService.getById(9999));
    }

    @Test
    public void testGetAllParameters() {
        parameterService.create(parameterDto);
        assertThat(parameterService.getAll()).isNotEmpty();
    }

    @Test
    public void testUpdateParameter() {
        ParameterDto createdParameter = parameterService.create(parameterDto);
        createdParameter.setName("Updated Parameter");
        parameterService.updateById(createdParameter.getId(), createdParameter);

        ParameterDto updatedParameter = parameterService.getById(createdParameter.getId());
        assertThat(updatedParameter.getName()).isEqualTo("Updated Parameter");
    }

    @Test
    public void testDeleteParameter() {
        ParameterDto createdParameter = parameterService.create(parameterDto);
        parameterService.deleteById(createdParameter.getId());

        assertThrows(ServiceException.class, () -> parameterService.getById(createdParameter.getId()));
    }
}