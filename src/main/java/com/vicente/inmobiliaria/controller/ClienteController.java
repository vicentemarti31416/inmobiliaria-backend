package com.vicente.inmobiliaria.controller;

import com.vicente.inmobiliaria.entity.Cliente;
import com.vicente.inmobiliaria.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCliente(@PathVariable Long id) {
        Optional<Cliente> optional = clienteService.getCliente(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optional.get());
    }

    @GetMapping("/")
    public ResponseEntity<?> getClientes() {
        return ResponseEntity.ok(clienteService.getClientes());
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getClientesPageable(@PathVariable Integer page) {
        Page<Cliente> clientePage = clienteService.getClientesPageable(PageRequest.of(page, 10));
        return ResponseEntity.ok(clientePage);
    }

    @PostMapping("/")
    public ResponseEntity<?> addCliente(@Validated @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) return this.validate(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.addCliente(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@Validated @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) return this.validate(result);
        Optional<Cliente> optional = clienteService.getCliente(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        Cliente clienteDb = optional.get();
        clienteDb.setName(cliente.getName());
        clienteDb.setLastname(cliente.getLastname());
        clienteDb.setEmail(cliente.getEmail());
        clienteDb.setPhone(cliente.getPhone());
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.addCliente(clienteDb));
    }

    protected ResponseEntity<?> validate(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> errores.put(error.getField(), "El campo: ".concat(error.getField()).concat(Objects.requireNonNull(error.getDefaultMessage()))));
        return ResponseEntity.badRequest().body(errores);
    }

}
