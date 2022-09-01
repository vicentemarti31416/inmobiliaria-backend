package com.vicente.inmobiliaria.security.controller;

import com.vicente.inmobiliaria.security.dto.JwtDto;
import com.vicente.inmobiliaria.security.dto.LoginUsuario;
import com.vicente.inmobiliaria.security.dto.NuevoUsuario;
import com.vicente.inmobiliaria.security.entity.Rol;
import com.vicente.inmobiliaria.security.entity.Usuario;
import com.vicente.inmobiliaria.security.enums.RolNombre;
import com.vicente.inmobiliaria.security.jwt.JwtProvider;
import com.vicente.inmobiliaria.security.service.RolService;
import com.vicente.inmobiliaria.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Validated @RequestBody NuevoUsuario nuevoUsuario, BindingResult result) {
        if (result.hasErrors()) return this.validar(result);
        if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) return new ResponseEntity<>("El usuario ya existe", HttpStatus.CONFLICT);
        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) return new ResponseEntity<>("El email ya se está usando", HttpStatus.CONFLICT);
        Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.findByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.findByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginUsuario loginUsuario, BindingResult result) {
        if (result.hasErrors()) return this.validar(result);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }

    protected ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach( error ->
                errores.put(error.getField(), "El campo ".concat(error.getField().concat(" ").concat(Objects.requireNonNull(error.getDefaultMessage()))))
        );
        return ResponseEntity.badRequest().body(errores);
    }
}
