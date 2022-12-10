package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping()
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/user")
    public String showUser(Principal principal, Model model){
        User user = userService.findByName(principal.getName());
        model.addAttribute("user",user);
        return "forUser/user";
    }

    @GetMapping("/admin")
    public String index( Model model,Principal principal){
        User admin = userService.findByName(principal.getName());
        model.addAttribute("admin",admin);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user",new User());
        model.addAttribute("roles",roleService.getAllByRole());
        return "user/adminPage";
    }
    @PostMapping("/admin")
    public String create(@ModelAttribute("user")@Valid User user, BindingResult bindingResult,@RequestParam("role") List<String> roles){
        if(bindingResult.hasErrors() && userService.existUser(user.getName())){
            return "redirect:/user/adminPage";
        }
        user.setRoles(Collections.singletonList(roleService.findByRole(roles.get(0))));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        return "redirect:/admin";
    }
    @GetMapping("/admin/{id}")
    public String show(Model model, @PathVariable("id")int id){
        model.addAttribute("user", userService.findOne(id));
        return "user/show";
    }
    @GetMapping("/admin/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("user", userService.findOne(id));
        return "user/edit";
    }
    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user")@Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id")int id,@RequestParam("role") List<String> roles){
        if(bindingResult.hasErrors()&& userService.existUser(user.getName())){
            return "redirect:/user/adminPage";
        }

        user.setRoles(Collections.singletonList(roleService.findByRole(roles.get(0))));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(id,user);
        return "redirect:/admin";
    }
    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id")int id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
