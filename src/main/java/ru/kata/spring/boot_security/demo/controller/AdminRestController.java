package ru.kata.spring.boot_security.demo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.util.UserNameExistsException;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;
import ru.kata.spring.boot_security.demo.util.UserValidationException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/")
public class AdminRestController {

    @Autowired
    private UserService userService;


    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUser() {
        List<User> userList = userService.getListUser();
        List<UserDTO> userDTOList = userList.stream().map(user -> convertToUserDTO(user)).toList();
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id) {
        UserDTO userDTO = convertToUserDTO(userService.readUser(id));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<UserDTO> getAuthUser(Principal principal) {
        UserDTO userDTO = convertToUserDTO(userService.findByUsername(principal.getName()));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<HttpStatus> addUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder messege = new StringBuilder();
            List<FieldError> error = new ArrayList<>();
            error.add(bindingResult.getFieldError());
            for (FieldError err : error) {
                messege.append(err.getDefaultMessage().toString());
            }
            throw new UserValidationException(error.toString());
        }
        userService.createUser(convertToUser(userDTO));
        return new ResponseEntity<>(HttpStatus.OK);


    }

    @PatchMapping()
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder messege = new StringBuilder();
            List<FieldError> error = new ArrayList<>();
            error.add(bindingResult.getFieldError());
            for (FieldError err : error) {
                messege.append(err.getDefaultMessage().toString());
            }
            throw new UserValidationException(error.toString());
        }
        userService.updateUser(convertToUser(userDTO));
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserNotFoundException e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse("User not found");
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserValidationException e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(e.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserNameExistsException e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(e.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private User convertToUser(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

    private UserDTO convertToUserDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }


}
