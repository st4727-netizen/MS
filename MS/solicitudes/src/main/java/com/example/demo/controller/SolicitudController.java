package com.example.demo.controller;

import com.example.demo.model.dao.SolicitudDAO;
import com.example.demo.model.entity.Solicitud;
import com.example.demo.dto.SolicitudDTO;
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

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<Solicitud> obtenerPorId(@PathVariable Integer id) {
        Optional<Solicitud> solicitud = solicitudDAO.findById(id);
        return solicitud.filter(Solicitud::getEstado)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Solicitud> obtenerPorCliente(@PathVariable Integer idCliente) {
        return solicitudDAO.findByIdClienteAndEstado(idCliente, true);
    }

    @PostMapping
    public ResponseEntity<Solicitud> crearSolicitud(@RequestBody SolicitudDTO solicitudDTO) {
        try {
            // Creamos la entidad a partir del DTO
            Solicitud solicitud = new Solicitud();
            solicitud.setIdCliente(solicitudDTO.getIdCliente());
            solicitud.setIdEstatus(solicitudDTO.getIdEstatus());
            solicitud.setFecha(solicitudDTO.getFecha());
            solicitud.setEstado(true); // siempre activa al crear

            // Guardamos en la DB
            Solicitud nuevaSolicitud = solicitudDAO.save(solicitud);

            return ResponseEntity.ok(nuevaSolicitud);
        } catch (Exception e) {
            e.printStackTrace(); // Para ver errores exactos
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/beacon/{id}")
    public ResponseEntity<Void> actualizarConBeacon(
            @PathVariable Integer id,
            @RequestParam("idEstatus") Integer idEstatus
    ) {
        try {
            Optional<Solicitud> existenteOpt = solicitudDAO.findById(id);
            if (existenteOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Solicitud existente = existenteOpt.get();

            // Solo si sigue activa
            if (!existente.getEstado()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            // Cambiar el estatus únicamente
            existente.setId_estatus(idEstatus);

            solicitudDAO.save(existente);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Integer id, @RequestBody Solicitud solicitud) {
        Optional<Solicitud> existenteOpt = solicitudDAO.findById(id);
        if (existenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Solicitud existente = existenteOpt.get();

        if (!existente.getEstado()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if (solicitud.getIdCliente() != null)
            existente.setIdCliente(solicitud.getIdCliente());
        if (solicitud.getIdEstatus() != null)       // 🔹 Aquí
            existente.setIdEstatus(solicitud.getIdEstatus()); // 🔹 Aquí
        if (solicitud.getFecha() != null)
            existente.setFecha(solicitud.getFecha());
        if (solicitud.getEstado() != null)
            existente.setEstado(solicitud.getEstado());

        Solicitud actualizada = solicitudDAO.save(existente);
        return ResponseEntity.ok(actualizada);
    }


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
