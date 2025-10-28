package com.example.demo.service;

import com.example.demo.model.entity.VideoDeConsentimiento;

import java.util.List;
import java.util.Optional;

public interface VideoDeConsentimientoService {
    List<VideoDeConsentimiento> findAll();
    Optional<VideoDeConsentimiento> findById(Integer id);
    VideoDeConsentimiento save(VideoDeConsentimiento video);
    void deleteById(Integer id);
}

