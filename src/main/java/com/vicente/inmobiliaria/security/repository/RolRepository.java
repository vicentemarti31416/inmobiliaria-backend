package com.vicente.inmobiliaria.security.repository;

import com.vicente.inmobiliaria.security.entity.Rol;
import com.vicente.inmobiliaria.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
