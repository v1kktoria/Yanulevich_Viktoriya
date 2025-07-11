package senla.service;


import senla.dto.UserDto;
import senla.model.User;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto getById(Integer id);

    List<UserDto> getAll();

    void updateById(Integer id, UserDto userDto);

    void deleteById(Integer id);
}
