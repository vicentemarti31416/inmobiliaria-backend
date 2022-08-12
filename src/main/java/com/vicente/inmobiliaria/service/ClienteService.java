package com.vicente.inmobiliaria.service;

import com.vicente.inmobiliaria.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Optional<Cliente> getCliente(Long id);
    List<Cliente> getClientes();
    Cliente addCliente(Cliente cliente);
    void deleteCliente(Long id);
}
