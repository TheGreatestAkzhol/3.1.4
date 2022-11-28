package ru.kata.spring.boot_security.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class ForUsersController {
    private final UserRepository userRepository;

    @Autowired
    public ForUsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String showUser(Principal principal, Model model){
        User user = userRepository.findByName(principal.getName());
        model.addAttribute("user",user);
        return "forUser/user";
    }
}
