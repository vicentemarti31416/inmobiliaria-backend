package com.vicente.inmobiliaria.security.enums.service;

import com.vicente.inmobiliaria.entity.Vivienda;
import com.vicente.inmobiliaria.repository.ViviendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ViviendaServiceImpl implements ViviendaService {

    @Autowired
    private ViviendaRepository viviendaRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Vivienda> getVivienda(Long id) {
        return viviendaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vivienda> getViviendas() {
        return viviendaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Vivienda> getViviendasPageable(Pageable pageable) {
        return viviendaRepository.findAll(pageable);
    }

    @Override
    public Vivienda addVivienda(Vivienda vivienda) {
        return viviendaRepository.save(vivienda);
    }

    @Override
    public void deleteVivienda(Long id) {
        viviendaRepository.deleteById(id);
    }

}
