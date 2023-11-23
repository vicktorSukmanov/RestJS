package ru.kata.spring.boot_security.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

@Component
public class MapperUser implements Mapper {


    @Override
    public User convertToUser(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

    @Override
    public UserDTO convertToUserDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
