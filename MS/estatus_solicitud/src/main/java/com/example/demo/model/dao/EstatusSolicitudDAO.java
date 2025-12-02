package com.example.demo.model.dao;

import com.example.demo.model.entity.EstatusSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstatusSolicitudDAO extends JpaRepository<EstatusSolicitud, Integer> {
}





