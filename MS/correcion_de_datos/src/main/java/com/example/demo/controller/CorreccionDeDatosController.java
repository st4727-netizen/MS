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
@CrossOrigin(origins = "*")
public class CorreccionDeDatosController {

    @Autowired
    private CorreccionDeDatosDAO correccionDAO;

    @GetMapping
    public List<CorreccionDeDatos> getAll() {
        return correccionDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorreccionDeDatos> getById(@PathVariable Integer id) {
        return correccionDAO.findById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CorreccionDeDatos> create(@RequestBody CorreccionDeDatos correccion) {

        // ✅ Validación mínima necesaria
        if (correccion.getIdSolicitud() == null || correccion.getCampoModificado() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // ✅ fecha_correccion y estado se asignan solos en la entidad
        CorreccionDeDatos guardada = correccionDAO.save(correccion);
        return new ResponseEntity<>(guardada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorreccionDeDatos> update(@PathVariable Integer id,
                                                    @RequestBody CorreccionDeDatos nuevaCorreccion) {

        Optional<CorreccionDeDatos> existenteOpt = correccionDAO.findById(id);
        if (existenteOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CorreccionDeDatos existente = existenteOpt.get();

        if (nuevaCorreccion.getCampoModificado() != null)
            existente.setCampoModificado(nuevaCorreccion.getCampoModificado());

        if (nuevaCorreccion.getValorAnterior() != null)
            existente.setValorAnterior(nuevaCorreccion.getValorAnterior());

        if (nuevaCorreccion.getValorNuevo() != null)
            existente.setValorNuevo(nuevaCorreccion.getValorNuevo());

        if (nuevaCorreccion.getEstado() != null)
            existente.setEstado(nuevaCorreccion.getEstado());

        CorreccionDeDatos actualizada = correccionDAO.save(existente);
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<CorreccionDeDatos> correccionOpt = correccionDAO.findById(id);
        if (correccionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CorreccionDeDatos correccion = correccionOpt.get();
        correccion.setEstado(false);
        correccionDAO.save(correccion);

        return ResponseEntity.noContent().build();
    }
}
