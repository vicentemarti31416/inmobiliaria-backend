package com.vicente.inmobiliaria.security.service;

import com.vicente.inmobiliaria.security.entity.Role;
import com.vicente.inmobiliaria.security.entity.User;
import java.util.List;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getAllUsers(); // Falta la paginación
}
