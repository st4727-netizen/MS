package com.example.demo.service;

import com.example.demo.model.dao.VideoDeConsentimientoDAO;
import com.example.demo.model.entity.VideoDeConsentimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoDeConsentimientoServiceImpl implements VideoDeConsentimientoService {

    @Autowired
    private VideoDeConsentimientoDAO videoDAO;

    @Override
    public List<VideoDeConsentimiento> findAll() {
        return videoDAO.findAll();
    }

    @Override
    public Optional<VideoDeConsentimiento> findById(Integer id) {
        return videoDAO.findById(id);
    }

    @Override
    public VideoDeConsentimiento save(VideoDeConsentimiento video) {
        return videoDAO.save(video);
    }

    @Override
    public void deleteById(Integer id) {
        videoDAO.deleteById(id);
    }
}

