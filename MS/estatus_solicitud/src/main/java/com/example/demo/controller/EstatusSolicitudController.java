package com.example.demo.controller;

import com.example.demo.model.dao.EstatusSolicitudDAO;
import com.example.demo.model.entity.EstatusSolicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estatus")
public class EstatusSolicitudController {

    @Autowired
    private EstatusSolicitudDAO estatusDAO;

    @GetMapping
    public List<EstatusSolicitud> obtenerTodos() {
        return estatusDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstatusSolicitud> obtenerPorId(@PathVariable Integer id) {
        Optional<EstatusSolicitud> estatus = estatusDAO.findById(id);
        return estatus.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstatusSolicitud> crear(@RequestBody EstatusSolicitud estatus) {
        return new ResponseEntity<>(estatusDAO.save(estatus), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstatusSolicitud> actualizar(@PathVariable Integer id, @RequestBody EstatusSolicitud estatus) {
        if (estatusDAO.existsById(id)) {
            estatus.setId_estatus(id);
            return ResponseEntity.ok(estatusDAO.save(estatus));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<EstatusSolicitud> estatusOpt = estatusDAO.findById(id);
        if (estatusOpt.isPresent()) {
            EstatusSolicitud estatus = estatusOpt.get();
            estatus.setEstado(false); // Marcamos como inactivo
            estatusDAO.save(estatus); // Guardamos el cambio
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
