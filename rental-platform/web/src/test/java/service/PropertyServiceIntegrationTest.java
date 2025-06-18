package service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import senla.Main;
import senla.dto.PropertyDto;
import senla.exception.ServiceException;
import senla.model.constant.PropertyType;
import senla.service.PropertyService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Main.class)
@RequiredArgsConstructor
@Transactional
@ActiveProfiles("test")
public class PropertyServiceIntegrationTest {

    @Autowired
    private PropertyService propertyService;

    private PropertyDto propertyDto;

    @BeforeEach
    public void setUp() {
        propertyDto = new PropertyDto();
        propertyDto.setOwnerId(1);
        propertyDto.setType(PropertyType.APARTMENT);
        propertyDto.setArea(100);
        propertyDto.setPrice(56000);
    }

    @Test
    public void testCreateProperty() {
        PropertyDto createdProperty = propertyService.create(propertyDto);
        assertThat(createdProperty).isNotNull();
        assertThat(createdProperty.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetPropertyById() {
        PropertyDto createdProperty = propertyService.create(propertyDto);
        PropertyDto fetchedProperty = propertyService.getById(createdProperty.getId());
        assertThat(fetchedProperty).isNotNull();
        assertThat(fetchedProperty.getId()).isEqualTo(createdProperty.getId());
    }

    @Test
    public void testGetPropertyByIdNotFound() {
        assertThrows(ServiceException.class, () -> propertyService.getById(9999));
    }

    @Test
    public void testGetAllProperties() {
        propertyService.create(propertyDto);
        List<PropertyDto> allProperties = propertyService.getAll();
        assertThat(allProperties).isNotEmpty();
    }

    @Test
    public void testGetPropertiesByUserId() {
        propertyService.create(propertyDto);
        List<PropertyDto> userProperties = propertyService.getByUserId(1);
        assertThat(userProperties).isNotEmpty();
    }

    @Test
    public void testUpdateProperty() {
        PropertyDto createdProperty = propertyService.create(propertyDto);
        createdProperty.setPrice(120000);
        propertyService.updateById(createdProperty.getId(), createdProperty);

        PropertyDto updatedProperty = propertyService.getById(createdProperty.getId());
        assertThat(updatedProperty.getPrice()).isEqualTo(120000);
    }

    @Test
    public void testDeleteProperty() {
        PropertyDto createdProperty = propertyService.create(propertyDto);
        propertyService.deleteById(createdProperty.getId());

        assertThrows(ServiceException.class, () -> propertyService.getById(createdProperty.getId()));
    }

    @Test
    public void testGetAllWithEssentialDetails() {
        propertyService.create(propertyDto);
        List<PropertyDto> properties = propertyService.getAllWithEssentialDetails();
        assertThat(properties).isNotEmpty();
    }
}