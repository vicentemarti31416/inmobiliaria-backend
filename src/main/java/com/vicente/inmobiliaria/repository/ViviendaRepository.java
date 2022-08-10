package com.vicente.inmobiliaria.repository;

import com.vicente.inmobiliaria.entity.Vivienda;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "viviendas")
public interface ViviendaRepository extends PagingAndSortingRepository<Vivienda, Long> {

}
