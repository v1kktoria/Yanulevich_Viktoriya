package senla.service;

import senla.dto.AddressDto;

import java.util.List;

public interface AddressService {

    AddressDto create(AddressDto addressDto);

    AddressDto getById(Integer id);

    List<AddressDto> getByPropertyId(Integer id);

    List<AddressDto> getAll();

    void updateById(Integer id, AddressDto addressDto);

    void deleteById(Integer id);
}
