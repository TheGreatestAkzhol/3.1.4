package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceInterface;
import javax.validation.Valid;
import java.util.Collections;


@Controller
@RequestMapping("/admin")
public class UserController {
    private final UserServiceInterface userServiceInterface;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(UserServiceInterface userServiceInterface, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userServiceInterface = userServiceInterface;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping()
    public String index(Model model){
        model.addAttribute("users", userServiceInterface.findAll());
        return "user/index";
    }
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id")int id){
        model.addAttribute("user", userServiceInterface.findOne(id));
        return "user/show";
    }
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user){
        return "user/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("user")@Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors() && userServiceInterface.existUser(user.getName())){
            return "user/new";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRole("ROLE_USER").get();
        user.setRoles(Collections.singletonList(role));
        userServiceInterface.save(user);

        return "redirect:/admin";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("user", userServiceInterface.findOne(id));
        return "user/edit";
    }
    @PatchMapping("{id}")
    public String update(@ModelAttribute("user")@Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id")int id){
        if(bindingResult.hasErrors()&& userServiceInterface.existUser(user.getName())){
            return "user/edit";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRole("ROLE_USER").get();
        user.setRoles(Collections.singletonList(role));
        userServiceInterface.update(id,user);
        return "redirect:/admin";
    }
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id")int id){
        userServiceInterface.delete(id);
        return "redirect:/admin";
    }
}
