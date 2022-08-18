package com.vicente.inmobiliaria.controller;

import com.vicente.inmobiliaria.entity.Vivienda;
import com.vicente.inmobiliaria.service.ViviendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/viviendas")
public class ViviendaController {

    @Autowired
    private ViviendaService viviendaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getVivienda(@PathVariable Long id) {
        return ResponseEntity.ok(viviendaService.getVivienda(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getViviendas() {
        return ResponseEntity.ok(viviendaService.getViviendas());
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getViviendasPageable(@PathVariable Integer page) {
        Page<Vivienda> viviendaPage = viviendaService.getViviendasPageable(PageRequest.of(page, 10));
        return ResponseEntity.ok(viviendaPage);
    }

    @PostMapping("/")
    public ResponseEntity<?> addVivienda(@Validated @RequestBody Vivienda vivienda, BindingResult result) {
        if (result.hasErrors()) return this.validate(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(viviendaService.addVivienda(vivienda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVivienda(@Validated @RequestBody Vivienda vivienda, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) this.validate(result);
        Optional<Vivienda> optional = viviendaService.getVivienda(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        Vivienda viviendaDb = optional.get();
        viviendaDb.setBedrooms(vivienda.getBedrooms());
        viviendaDb.setCity(vivienda.getCity());
        viviendaDb.setMeters(vivienda.getMeters());
        viviendaDb.setRestrooms(vivienda.getRestrooms());
        viviendaDb.setPrice(vivienda.getPrice());
        viviendaDb.setStreet(vivienda.getStreet());
        return ResponseEntity.status(HttpStatus.CREATED).body(viviendaService.addVivienda(viviendaDb));
    }

    protected ResponseEntity<?> validate(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> errores.put(error.getField(), "El campo: ".concat(error.getField()).concat(Objects.requireNonNull(error.getDefaultMessage()))));
        return ResponseEntity.badRequest().body(errores);
    }

}
