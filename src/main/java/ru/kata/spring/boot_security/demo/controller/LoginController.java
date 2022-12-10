package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
@Controller
@RequestMapping()
public class LoginController {
    private final RoleService roleService;
    @Autowired
    public LoginController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage(){
        roleService.getAllByRole().stream().forEach(s-> System.out.println(s));
        roleService.getAllByRole().stream().forEach(s-> System.out.println(s.getRole() + " role"));
        Role role = roleService.findByRole("ROLE_USER");
        System.out.println(role.getRole_id());
        return "/login";
    }
}
