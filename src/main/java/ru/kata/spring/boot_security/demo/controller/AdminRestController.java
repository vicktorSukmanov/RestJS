package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userService.getListUser(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.readUser(id), HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<User> getAuthUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @PostMapping()
//    public ResponseEntity<HttpStatus> addUser(@Valid @RequestBody User user) {
//        System.out.println(user.toString());
//        if (userService.createUser(user)) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            StringBuilder messege = new StringBuilder();
            List <FieldError> error = new ArrayList<>();
            error.add(bindingResult.getFieldError());
            for (FieldError err:error){
                        messege.append(err.getDefaultMessage().toString());
            }
            throw new UserValidationException(error.toString());
        }
        userService.createUser(user);
            return new ResponseEntity<>(HttpStatus.OK);


    }

    @PatchMapping()
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            StringBuilder messege = new StringBuilder();
            List <FieldError> error = new ArrayList<>();
            error.add(bindingResult.getFieldError());
            for (FieldError err:error){
                messege.append(err.getDefaultMessage().toString());
            }
            throw new UserValidationException(error.toString());
        }
        userService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserNotFoundException e){
        UserErrorResponse userErrorResponse = new UserErrorResponse("User not found");
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserValidationException e){
        UserErrorResponse userErrorResponse = new UserErrorResponse(e.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserNameExistsException e){
        UserErrorResponse userErrorResponse = new UserErrorResponse(e.getMessage());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }



}
