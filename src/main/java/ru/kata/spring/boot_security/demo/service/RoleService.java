package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.Optional;

public interface RoleService{
   Role findByRole(String role);
}
