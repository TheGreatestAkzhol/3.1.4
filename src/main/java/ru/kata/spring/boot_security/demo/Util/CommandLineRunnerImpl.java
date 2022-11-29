package ru.kata.spring.boot_security.demo.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserService userService;
    private final RoleRepository roleRepository;
    @Autowired
    public CommandLineRunnerImpl(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);
        User user = new User();
        user.setId(1);
        user.setName("User");
        user.setAge(22);
        user.setEmail("user@mail.com");
        user.setPassword("$2a$12$gR0320AoQJavlksl.nWaiO3mpfzgzU8qJWcrZNhwW08n4cAqncWFq");
        //password:user
        Role role = roleRepository.findByRole("ROLE_USER");
        user.setRoles(Collections.singletonList(role));
        userService.save(user);
        User admin = new User();
        admin.setId(2);
        admin.setName("Admin");
        admin.setAge(22);
        admin.setEmail("admin@mail.com");
        admin.setPassword("$2a$12$gR0320AoQJavlksl.nWaiO3mpfzgzU8qJWcrZNhwW08n4cAqncWFq");
        //password:user
        Role role1 = roleRepository.findByRole("ROLE_ADMIN");
        admin.setRoles(Collections.singletonList(role1));
        userService.save(admin);
    }
}