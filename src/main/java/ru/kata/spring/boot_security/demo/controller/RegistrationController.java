package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.NoResultException;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registerView(User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        try {
            if (user.getEmail().equals(userService.findByEmail(user.getEmail()).getEmail())) {
                model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
                return "registration";
            }
        } catch (NoResultException | EmptyResultDataAccessException r) {
        }

        userService.add(user);
        return "redirect:/login";
    }

}
