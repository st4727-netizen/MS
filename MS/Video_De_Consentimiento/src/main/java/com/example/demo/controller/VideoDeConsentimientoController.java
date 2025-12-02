package com.example.demo.controller;

import com.example.demo.model.entity.VideoDeConsentimiento;
import com.example.demo.service.VideoDeConsentimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<VideoDeConsentimiento> crear(@RequestBody VideoDeConsentimiento video) {
        video.setIdVideo(null);
        VideoDeConsentimiento nuevo = videoService.save(video); // <-- YA CORREGIDO
        return ResponseEntity.ok(nuevo);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadVideo(
            @RequestParam("file") MultipartFile video,
            @RequestParam("idSolicitud") Integer idSolicitud
    ) {
        try {
            // Guarda el video en una carpeta local
            String fileName = System.currentTimeMillis() + "_" + video.getOriginalFilename();
            String uploadPath = "videos_subidos/" + fileName;

            java.nio.file.Path path = java.nio.file.Paths.get(uploadPath);
            java.nio.file.Files.createDirectories(path.getParent());
            java.nio.file.Files.write(path, video.getBytes());

            // Crea el registro en la DB
            VideoDeConsentimiento nuevo = new VideoDeConsentimiento();
            nuevo.setIdSolicitud(idSolicitud);
            nuevo.setFecha(new java.sql.Timestamp(System.currentTimeMillis()));
            nuevo.setUrlVideo(uploadPath);
            nuevo.setEstado(true);

            VideoDeConsentimiento guardado = videoService.save(nuevo);

            // Respuesta correcta
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el video");
        }
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


