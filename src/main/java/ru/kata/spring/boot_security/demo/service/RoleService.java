package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService{
   Role findByRole(String role);
   List<Role> getAllByRole();
}
