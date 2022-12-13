package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        System.out.println("@@@@контролер админ гет отработал");
        return "admin";
    }

    @DeleteMapping("/users/delete/{id}")
    public String  deleteUser(@PathVariable(value="id") Long id, Model model) {
        System.out.println("@@@@@  contr delete hase worked");
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
