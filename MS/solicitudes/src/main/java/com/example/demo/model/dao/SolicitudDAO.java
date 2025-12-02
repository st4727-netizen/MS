package com.example.demo.model.dao;

import com.example.demo.model.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudDAO extends JpaRepository<Solicitud, Integer> {
	List<Solicitud> findByIdClienteAndEstado(Integer idCliente, Boolean estado);
}



