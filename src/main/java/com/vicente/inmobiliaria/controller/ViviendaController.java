package com.vicente.inmobiliaria.controller;

import com.vicente.inmobiliaria.entity.Vivienda;
import com.vicente.inmobiliaria.service.ViviendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/viviendas")
public class ViviendaController {

    @Autowired
    private ViviendaService viviendaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getVivienda(@PathVariable Long id) {
        Optional<Vivienda> optional = viviendaService.getVivienda(id);
        if(optional.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(viviendaService.getVivienda(id).get());
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

    @GetMapping("/photo/{id}")
    public ResponseEntity<?> getPhoto(@PathVariable Long id) {
        Optional<Vivienda> optional = viviendaService.getVivienda(id);
        if(optional.isEmpty() || optional.get().getPhoto() == null) return ResponseEntity.notFound().build();
        Resource resource = new ByteArrayResource(optional.get().getPhoto());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }

    @PostMapping("/")
    public ResponseEntity<?> addVivienda(@Validated @RequestBody Vivienda vivienda, BindingResult result) {
        if (result.hasErrors())
            return this.validate(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(viviendaService.addVivienda(vivienda));
    }

    // Este método es válido para guardar las fotos en la base de datos
    @PostMapping("/photo")
    public ResponseEntity<?> addViviendaWithPhoto(@Validated Vivienda vivienda, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
        if(!archivo.isEmpty()) vivienda.setPhoto(archivo.getBytes());
        return ResponseEntity.status(HttpStatus.CREATED).body(viviendaService.addVivienda(vivienda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVivienda(@Validated @RequestBody Vivienda vivienda, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) this.validate(result);
        Optional<Vivienda> optional = viviendaService.getVivienda(id);
        if (optional.isEmpty())return ResponseEntity.notFound().build();
        Vivienda viviendaDb = optional.get();
        viviendaDb.setBedrooms(vivienda.getBedrooms());
        viviendaDb.setCity(vivienda.getCity());
        viviendaDb.setMeters(vivienda.getMeters());
        viviendaDb.setRestrooms(vivienda.getRestrooms());
        viviendaDb.setPrice(vivienda.getPrice());
        viviendaDb.setStreet(vivienda.getStreet());
        return ResponseEntity.status(HttpStatus.CREATED).body(viviendaService.addVivienda(viviendaDb));
    }

    @PutMapping("/photo/{id}")
    public ResponseEntity<?> updateViviendaWithPhoto(@Validated Vivienda vivienda, BindingResult result, @PathVariable Long id, @RequestParam MultipartFile archivo) throws IOException {
        if (result.hasErrors()) this.validate(result);
        Optional<Vivienda> optional = viviendaService.getVivienda(id);
        if (optional.isEmpty())return ResponseEntity.notFound().build();
        Vivienda viviendaDb = optional.get();
        viviendaDb.setBedrooms(vivienda.getBedrooms());
        viviendaDb.setCity(vivienda.getCity());
        viviendaDb.setMeters(vivienda.getMeters());
        viviendaDb.setRestrooms(vivienda.getRestrooms());
        viviendaDb.setPrice(vivienda.getPrice());
        viviendaDb.setStreet(vivienda.getStreet());
        if(!archivo.isEmpty())viviendaDb.setPhoto(archivo.getBytes());
        return ResponseEntity.status(HttpStatus.CREATED).body(viviendaService.addVivienda(viviendaDb));
    }

    // Este método sólo es válido cuando tenemos las fotos en el propio proyecto
    /*
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Vivienda> optional = viviendaService.getVivienda(id);
        Vivienda vivienda = null;
        if(optional.isPresent()) vivienda = optional.get();
        if (!archivo.isEmpty()) {
            String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
            Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (IOException e) {
                return ResponseEntity.internalServerError().build();
            }
            String nombreFotoAnterior = vivienda.getPicture();
            if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
                Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaFotoAnterior.toFile();
                if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                    archivoFotoAnterior.delete();
                }
            }
            vivienda.setPicture(nombreArchivo);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(viviendaService.addVivienda(vivienda));
    }
    */


    // Este método sólo es válido cuando tenemos las fotos en el propio proyecto
    /*
    @GetMapping("/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
        Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
        Resource recurso = null;
        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(!recurso.exists() && !recurso.isReadable()) {
            throw new RuntimeException("Error no se pudo cargar la imagen: " + nombreFoto);
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }
    */

    protected ResponseEntity<?> validate(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> errores.put(error.getField(), "El campo: ".concat(error.getField()).concat(Objects.requireNonNull(error.getDefaultMessage()))));
        return ResponseEntity.badRequest().body(errores);
    }

}
