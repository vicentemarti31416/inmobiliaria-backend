package com.vicente.inmobiliaria.service;

import com.vicente.inmobiliaria.entity.Vivienda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ViviendaService {

    Optional<Vivienda> getVivienda(Long id);
    List<Vivienda> getViviendas();
    Page<Vivienda> getViviendasPageable(Pageable pageable);
    Vivienda addVivienda(Vivienda vivienda);
    void deleteVivienda(Long id);
}
