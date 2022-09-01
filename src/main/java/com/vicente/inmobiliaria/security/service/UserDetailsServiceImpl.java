package com.vicente.inmobiliaria.security.service;

import com.vicente.inmobiliaria.security.entity.Usuario;
import com.vicente.inmobiliaria.security.entity.UsuarioPrincipal;
import com.vicente.inmobiliaria.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(username).get();
        return UsuarioPrincipal.build(usuario);
    }
}
