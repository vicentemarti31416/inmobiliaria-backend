package com.vicente.inmobiliaria.repository;

import com.vicente.inmobiliaria.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "clientes")
public interface ClientesRepository extends PagingAndSortingRepository<Cliente, Long> {
}
