package com.vicente.inmobiliaria.security.service;

import com.vicente.inmobiliaria.security.entity.Role;
import com.vicente.inmobiliaria.security.entity.User;
import com.vicente.inmobiliaria.security.repository.RoleRepository;
import com.vicente.inmobiliaria.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        Optional<User> optional = userRepository.findByUsername(username);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        }
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(String username) {
        Optional<User> optional = userRepository.findByUsername(username);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthorities(user));
    }

    private static List<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(userRoles);
    }
}
