package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = "/admin")
    public String getAllUser(Model model, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        List<User> users = userService.getListUser();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("this_user", user);
        model.addAttribute("roles", roles);
        return "admin";
    }


    @PostMapping("/admin")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleRepository.findAll();
            User this_user = (User) userService.loadUserByUsername(principal.getName());
            model.addAttribute("roles", roles);
            model.addAttribute("this_user", this_user);
            return "admin";
        }
        if (userService.createUser(user)) {
            return "redirect:/admin";
        } else {
            return "userexist";
        }
    }

    @PostMapping("admin/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleRepository.findAll();
            User this_user = (User) userService.loadUserByUsername(principal.getName());
            model.addAttribute("roles", roles);
            model.addAttribute("this_user", this_user);
            return "admin";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
