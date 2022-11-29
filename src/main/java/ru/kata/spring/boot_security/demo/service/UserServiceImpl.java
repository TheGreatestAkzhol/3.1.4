package ru.kata.spring.boot_security.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User findOne(int id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }
    @Transactional
    public void save(User user){
        userRepository.save(user);
    }
    @Transactional
    public void update(int id,User user){
        user.setId(id);
        userRepository.save(user);
    }
    @Transactional
    public void delete(int id){
        userRepository.deleteById(id);
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user =userRepository.findByName(name);
        if(user == null){
            throw new UsernameNotFoundException(String.format("User '%s' not found",name));
        }
        return user;
    }
    @Transactional
    public boolean existUser(String name){
        return userRepository.existsByName(name);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
