package com.example.demo.model.dao;

import com.example.demo.model.entity.CorreccionDeDatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorreccionDeDatosDAO extends JpaRepository<CorreccionDeDatos, Integer> {
}

