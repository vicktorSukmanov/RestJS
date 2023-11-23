package ru.kata.spring.boot_security.demo.mapper;

import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

public interface Mapper {

    User convertToUser(UserDTO userDTO);

    UserDTO convertToUserDTO(User user);

}
