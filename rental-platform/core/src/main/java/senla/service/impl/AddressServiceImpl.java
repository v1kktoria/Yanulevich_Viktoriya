package senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.dto.AddressDto;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Address;
import senla.model.Property;
import senla.repository.AddressRepository;
import senla.repository.PropertyRepository;
import senla.service.AddressService;
import senla.util.mappers.AddressMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final PropertyRepository propertyRepository;

    private final AddressMapper addressMapper;

    @Transactional
    @Override
    public AddressDto create(AddressDto addressDto) {
        Property property = propertyRepository.findById(addressDto.getPropertyId())
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, addressDto.getPropertyId()));

        if (addressRepository.existsByPropertyId(property.getId())) {
            throw new ServiceException(ServiceExceptionEnum.ADDRESS_ALREADY_EXISTS, property.getId());
        }
        Address address = addressMapper.toEntity(addressDto, property);
        AddressDto createdAddress = addressMapper.toDto(addressRepository.save(address));
        log.info("Адрес успешно создан с ID: {}", createdAddress.getId());
        return createdAddress;
    }

    @Override
    public AddressDto getById(Integer id) {
        AddressDto address = addressMapper.toDto(addressRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id)));
        log.info("Адрес успешно получен: {}", address);
        return address;
    }

    @Override
    public List<AddressDto> getByPropertyId(Integer id) {
        List<Address> addresses = addressRepository.findAllByPropertyId(id);
        List<AddressDto> addressDtos = addresses.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} адресов для недвижимости с ID: {}", addressDtos.size(), id);
        return addressDtos;
    }

    @Override
    public List<AddressDto> getAll() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressDto> addressDtos = addresses.stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
        log.info("Найдено {} адресов", addressDtos.size());
        return addressDtos;
    }

    @Transactional
    @Override
    public void updateById(Integer id, AddressDto addressDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));

        addressDto.setId(id);
        addressMapper.updateEntity(addressDto, address);
        addressRepository.save(address);
        log.info("Адрес с ID: {} успешно обновлен", id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.ENTITY_NOT_FOUND, id));
        addressRepository.delete(address);
        log.info("Адрес с ID: {} успешно удален", id);
    }
}
