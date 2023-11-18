package ru.kata.spring.boot_security.demo.controller;

import org.hibernate.annotations.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(userService.getListUser(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id){
        return new ResponseEntity<>(userService.readUser(id),HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<User> getAuthUser(Principal principal){
        User user =(User) userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user){
        userService.createUser(user);
        return new  ResponseEntity<>(HttpStatus.OK);

    }

    @PatchMapping()
    public ResponseEntity<HttpStatus> updateUser(@RequestBody  User user){
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id ){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
