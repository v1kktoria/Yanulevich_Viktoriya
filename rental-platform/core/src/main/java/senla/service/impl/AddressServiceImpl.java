package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dao.AddressDao;
import senla.dao.PropertyDao;
import senla.dto.AddressDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Address;
import senla.model.Property;
import senla.service.AddressService;
import senla.util.mappers.AddressMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@MeasureExecutionTime
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    private final PropertyDao propertyDao;

    private final AddressMapper addressMapper;

    @Override
    public AddressDto create(AddressDto addressDto) {
        Property property = propertyDao.findById(addressDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, addressDto.getPropertyId()));

        Address address = addressMapper.toEntity(addressDto, property);
        AddressDto createdAddress = addressMapper.toDto(addressDao.save(address));
        log.info("Адрес успешно создан с ID: {}", createdAddress.getId());
        return createdAddress;
    }

    @Override
    public AddressDto getById(Integer id) {
        AddressDto address = addressMapper.toDto(addressDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Адрес успешно получен: {}", address);
        return address;
    }

    @Override
    public List<AddressDto> getByPropertyId(Integer id) {
        List<Address> addresses = addressDao.findByPropertyId(id);
        List<AddressDto> addressDtos = addresses.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} адресов для недвижимости с ID: {}", addressDtos.size(), id);
        return addressDtos;
    }

    @Override
    public List<AddressDto> getAll() {
        List<Address> addresses = addressDao.findAll();
        List<AddressDto> addressDtos = addresses.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} адресов", addressDtos.size());
        return addressDtos;
    }

    @Override
    public void updateById(Integer id, AddressDto addressDto) {
        Address address = addressDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        addressDto.setId(id);
        addressMapper.updateEntity(addressDto, address);
        addressDao.update(address);
        log.info("Адрес с ID: {} успешно обновлен", id);
    }

    @Override
    public void deleteById(Integer id) {
        Address address = addressDao.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        addressDao.delete(address);
        log.info("Адрес с ID: {} успешно удален", id);
    }
}
