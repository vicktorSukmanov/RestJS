package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping(value = "/admin")
    public String getAllUser(Model model) {
        List<User> users = userService.getListUser();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "admin/new")
    public String getNewUser(Model model) {
        model.addAttribute(new User());
        List<Role> roles =  roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "new";

    }

    @PostMapping("/admin")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
       if (bindingResult.hasErrors()){
           List<Role> roles =  roleRepository.findAll();
           model.addAttribute("roles", roles);
           return "new";
       }
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/edit/")
    public String getUpdateUser(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.readUser(id));
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("roles", roles);

        return "edit";
    }

    @PostMapping("admin/{id}")
    public String updateUser(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List<Role> roles =  roleRepository.findAll();
            model.addAttribute("roles", roles);
            return "edit";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("admin/delete/")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
