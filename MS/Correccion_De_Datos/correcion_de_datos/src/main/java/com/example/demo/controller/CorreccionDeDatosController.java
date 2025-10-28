package com.example.demo.controller;

import com.example.demo.model.dao.CorreccionDeDatosDAO;
import com.example.demo.model.entity.CorreccionDeDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/correcciones")
public class CorreccionDeDatosController {

    @Autowired
    private CorreccionDeDatosDAO correccionDAO;

    @GetMapping
    public List<CorreccionDeDatos> getAll() {
        return correccionDAO.findAll();
    }

    @GetMapping("/{id}")
    public Optional<CorreccionDeDatos> getById(@PathVariable Integer id) {
        return correccionDAO.findById(id);
    }

    @PostMapping
    public CorreccionDeDatos create(@RequestBody CorreccionDeDatos correccion) {
        return correccionDAO.save(correccion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorreccionDeDatos> update(@PathVariable Integer id, @RequestBody CorreccionDeDatos nuevaCorreccion) {
        Optional<CorreccionDeDatos> existenteOpt = correccionDAO.findById(id);
        if (existenteOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CorreccionDeDatos existente = existenteOpt.get();

        // Solo actualiza campos enviados (opcional)
        if (nuevaCorreccion.getCampoModificado() != null)
            existente.setCampoModificado(nuevaCorreccion.getCampoModificado());
        if (nuevaCorreccion.getValorAnterior() != null)
            existente.setValorAnterior(nuevaCorreccion.getValorAnterior());
        if (nuevaCorreccion.getValorNuevo() != null)
            existente.setValorNuevo(nuevaCorreccion.getValorNuevo());
        if (nuevaCorreccion.getFechaCorreccion() != null)
            existente.setFechaCorreccion(nuevaCorreccion.getFechaCorreccion());
        if (nuevaCorreccion.getEstado() != null)
            existente.setEstado(nuevaCorreccion.getEstado());

        CorreccionDeDatos actualizada = correccionDAO.save(existente);
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<CorreccionDeDatos> correccionOpt = correccionDAO.findById(id);
        if (correccionOpt.isPresent()) {
            CorreccionDeDatos correccion = correccionOpt.get();
            correccion.setEstado(false); 
            correccionDAO.save(correccion);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

