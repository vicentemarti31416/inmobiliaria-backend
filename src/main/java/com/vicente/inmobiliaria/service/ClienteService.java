package com.vicente.inmobiliaria.service;

import com.vicente.inmobiliaria.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Optional<Cliente> getCliente(Long id);
    List<Cliente> getClientes();
    Page<Cliente> getClientesPageable(Pageable pageable);
    Cliente addCliente(Cliente cliente);
    void deleteCliente(Long id);
}
