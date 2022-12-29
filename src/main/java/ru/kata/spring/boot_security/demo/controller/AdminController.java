package ru.kata.spring.boot_security.demo.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    //получить всех пользователей
    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    //удалитьпользователя
    @DeleteMapping("/users/delete/{id}")
    public String  deleteUser(@PathVariable(value="id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    //edit user
    @GetMapping(value ="/users/edit/{id}")
    public String editPage(@PathVariable(value="id") Long id, Model model) {
        User usr = userService.findUserById(id);
        model.addAttribute("eduser", usr);
        return "edit";
    }

    //edit user
    @PostMapping(value ="/users/edit/{id}")
    public String editUser(@ModelAttribute("eduser") User eduser, @PathVariable(value="id") Long id) {
        userService.updateUser(eduser);
        return "redirect:/admin";
    }

    //регистр нового польз
    @GetMapping("/registration")
    public String registration(Model model) {
        User usr = new User();
        model.addAttribute("userForm", usr);
        return "registration";
    }

    //регистр нового польз
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            System.out.println(bindingResult);
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            System.out.println(bindingResult);
            return "registration";
        }
        return "redirect:/admin";
    }

}
