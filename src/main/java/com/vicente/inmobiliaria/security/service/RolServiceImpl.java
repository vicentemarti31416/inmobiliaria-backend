package com.vicente.inmobiliaria.security.service;

import com.vicente.inmobiliaria.security.entity.Rol;
import com.vicente.inmobiliaria.security.enums.RolNombre;
import com.vicente.inmobiliaria.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Rol> findByRolNombre(RolNombre rolNombre) {
        return rolRepository.findByRolNombre(rolNombre);
    }
}
