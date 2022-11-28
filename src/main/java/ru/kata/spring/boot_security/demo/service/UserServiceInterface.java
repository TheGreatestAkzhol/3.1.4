package ru.kata.spring.boot_security.demo.service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {
    List<User> findAll();
    User findOne(int id);
    void save(User user);
    void update(int id,User user);
    void delete(int id);
    boolean existUser(String name);
}
