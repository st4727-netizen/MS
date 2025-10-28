package com.example.demo.controller;

import com.example.demo.model.dao.SolicitudDAO;
import com.example.demo.model.entity.Solicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudDAO solicitudDAO;

    // Solo devuelve solicitudes activas
    @GetMapping
    public List<Solicitud> obtenerTodas() {
        return solicitudDAO.findAll()
                .stream()
                .filter(Solicitud::getEstado)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> obtenerPorId(@PathVariable Integer id) {
        Optional<Solicitud> solicitud = solicitudDAO.findById(id);
        return solicitud.filter(Solicitud::getEstado) // solo si está activa
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Solicitud> crear(@RequestBody Solicitud solicitud) {
        solicitud.setEstado(true); //por default se creará con el estado activo
        Solicitud guardada = solicitudDAO.save(solicitud);
        return new ResponseEntity<>(guardada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Integer id, @RequestBody Solicitud solicitud) {
        Optional<Solicitud> existenteOpt = solicitudDAO.findById(id);
        if (existenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Solicitud existente = existenteOpt.get();

        if (!existente.getEstado()) {
            // No se puede modificar si está "eliminada"
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Sirve para actualizar los campos
        if (solicitud.getId_cliente() != null)
            existente.setId_cliente(solicitud.getId_cliente());
        if (solicitud.getId_estatus() != null)
            existente.setId_estatus(solicitud.getId_estatus());
        if (solicitud.getFecha() != null)
            existente.setFecha(solicitud.getFecha());
        if (solicitud.getEstado() != null)
            existente.setEstado(solicitud.getEstado());

        Solicitud actualizada = solicitudDAO.save(existente);
        return ResponseEntity.ok(actualizada);
    }

    // delete, cambia el estado a false
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<Solicitud> solicitudOpt = solicitudDAO.findById(id);
        if (solicitudOpt.isPresent()) {
            Solicitud solicitud = solicitudOpt.get();
            solicitud.setEstado(false);
            solicitudDAO.save(solicitud);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
