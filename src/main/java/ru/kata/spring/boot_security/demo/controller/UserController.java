package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping(value = "/user")
    public String getUser(Principal principal, Model model) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("this_user", user);
        model.addAttribute("roles", roles);
        return "user";
    }
}
