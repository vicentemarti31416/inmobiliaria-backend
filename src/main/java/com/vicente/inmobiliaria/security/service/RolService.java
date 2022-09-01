package com.vicente.inmobiliaria.security.service;

import com.vicente.inmobiliaria.security.entity.Rol;
import com.vicente.inmobiliaria.security.enums.RolNombre;

import java.util.Optional;

public interface RolService {

    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
