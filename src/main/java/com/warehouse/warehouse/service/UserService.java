package com.warehouse.warehouse.service;

import com.warehouse.warehouse.repository.RoleRepository;
import com.warehouse.warehouse.repository.UserRepository;
import com.warehouse.warehouse.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(name);
        if (isNull(user)) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User());
    }

    public List<User> findAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public Boolean saveUser(User user) {
        if (checkIfUserExists(user)) {
            return false;
        }
        user.setRoles(Collections.singleton(roleRepository.getRoleById(1L)));
        String password = bCryptPasswordEncoder.encode(user.getPasswordConfirm());
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }

    private Boolean checkIfUserExists(User user) {
        User userFromDb = userRepository.findUserByLogin(user.getUsername());
        return nonNull(userFromDb);
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
