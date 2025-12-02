package com.example.demo.controller;

import com.example.demo.model.entity.VideoDeConsentimiento;
import com.example.demo.service.VideoDeConsentimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideoDeConsentimientoController {

    @Autowired
    private VideoDeConsentimientoService videoService;

    @GetMapping
    public List<VideoDeConsentimiento> listarTodos() {
        return videoService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<VideoDeConsentimiento> obtenerPorId(@PathVariable Integer id) {
        return videoService.findById(id);
    }

    @PostMapping
    public VideoDeConsentimiento crear(@RequestBody VideoDeConsentimiento video) {
        return videoService.save(video);
    }

    @PutMapping("/{id}")
    public VideoDeConsentimiento actualizar(@PathVariable Integer id, @RequestBody VideoDeConsentimiento video) {
        video.setIdVideo(id);
        return videoService.save(video);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        Optional<VideoDeConsentimiento> videoOpt = videoService.findById(id);
        if (videoOpt.isPresent()) {
            VideoDeConsentimiento video = videoOpt.get();
            video.setEstado(false); 
            videoService.save(video);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

