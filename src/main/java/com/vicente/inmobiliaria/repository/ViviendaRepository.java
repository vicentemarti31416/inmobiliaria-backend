package com.vicente.inmobiliaria.repository;

import com.vicente.inmobiliaria.entity.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViviendaRepository extends JpaRepository<Vivienda, Long> {
}
