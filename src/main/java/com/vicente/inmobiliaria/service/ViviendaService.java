package com.vicente.inmobiliaria.service;

import com.vicente.inmobiliaria.entity.Vivienda;

import java.util.List;
import java.util.Optional;

public interface ViviendaService {

    Optional<Vivienda> getVivienda(Long id);
    List<Vivienda> getViviendas();
    Vivienda addVivienda(Vivienda vivienda);
    void deleteVivienda(Long id);
}
