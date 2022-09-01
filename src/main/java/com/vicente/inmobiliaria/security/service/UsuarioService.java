package com.vicente.inmobiliaria.security.service;

import com.vicente.inmobiliaria.security.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);
    Usuario save(Usuario usuario);
}
